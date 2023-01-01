from collections import namedtuple
import json
import requests

base_url = "http://localhost:8080/api/"
path = lambda suffix: base_url + suffix

UserCredentials = namedtuple(
    "UserCredentials", [
        "username", 
        "password"
    ]
)

AccessToken = namedtuple(
    "AccessToken", [
        "user_id",
        "secret"
    ]
)

def credentials_headers(c: UserCredentials):
    return {
        "X-Auth-Username": c.username,
        "X-Auth-Password": c.password
    }

def access_token_headers(t: AccessToken):
    return {
        "X-Auth-User-ID":      str(t.user_id),
        "X-Auth-Access-Token": t.secret,
    }

PictureDraft = namedtuple(
    "PictureDraft", [
        "name",
        "data"
    ]
)

Point = namedtuple(
    "Point", [
        'x',
        'y'
    ]
)


def print_response(r: requests.Response):
    print("request ", r.request.method, r.request.url)
    print("status_code =", r.status_code)
    print("headers =", r.headers)
    try:
        obj = json.loads(r.text)
        print("data =", json.dumps(obj, indent=4))
    except Exception:
        print("data =", r.text)


def users_post(c: UserCredentials):
    return requests.post(
        url = path("users"),
        headers = credentials_headers(c)
    )


def users_get_by_name(username: str):
    return requests.get(
        url = path(f"users?name={username}")
    )

def pictures_post(t: AccessToken, draft: PictureDraft):
    return requests.post(
        url = path("pictures"),
        headers = access_token_headers(t),
        json = {
            "name": draft.name,
            "data": draft.data
        }
    )

def pictures_get_by_id(t: AccessToken, picture_id: int):
    return requests.get(
        url = path(f"pictures/{picture_id}"),
        headers = access_token_headers(t)
    )


def pictures_get_all_by_owner_id(t: AccessToken, owner_id: int):
    return requests.get(
        url = path(f"pictures?owner_id={owner_id}"),
        headers = access_token_headers(t)
    )


def pictures_taps_post(t: AccessToken, picture_id: int, point: Point):
    return requests.post(
        url = path(f"pictures/{picture_id}/taps"),
        headers = access_token_headers(t),
        json = {
            "x": point.x,
            "y": point.y
        }
    )


def pictures_taps_get_all_by_owner_id(
    t: AccessToken, picture_id: int, owner_id: int
): return requests.get(
    url = path(f"pictures/{picture_id}/taps?owner_id={owner_id}"),
    headers = access_token_headers(t)
)

def auth_access_tokens_get(c: UserCredentials):
    return requests.get(
        url = path(f"auth/access_tokens"),
        headers = credentials_headers(c)
    )

def register_user(credentials: UserCredentials):
    register_response = users_post(credentials)

    if (register_response.status_code == 200):
        token = register_response.json()
    else:
        token = auth_access_tokens_get(credentials).json()

    token = AccessToken(
        user_id = token.get("user_id"),
        secret  = token.get("secret")
    )   
    return token
    
    
if __name__ == "__main__":
    R = 100

    tester_username = "tester"
    tester_password = "qwerty123"
    tester_credentials = UserCredentials(tester_username, tester_password)
    tester_token = register_user(tester_credentials)
    
    anon_token = register_user(UserCredentials("anon", "lalala"))

    for response in [
        users_get_by_name(tester_username),
        pictures_post(tester_token, PictureDraft(
            name = "rectangle", 
            data = {
                "type": "colored",
                "color": "#0FFF00",
                "child": {
                    "type": "polygon",
                    "points": [
                        { "x": -R, "y": -R },
                        { "x":  R, "y": -R },
                        { "x":  R, "y":  R },
                        { "x": -R, "y":  R },
                    ]
                }
            }
        )),
        pictures_post(tester_token, PictureDraft(
            name = "legacy", 
            data = {
                "type": "colored",
                "color": "#FFFF00",
                "child": {
                    "type": "union",
                    "children": [
                        {
                            "type": "ellipse",
                            "center": {"x": 10, "y": 40},
                            "radius": {"x": 50, "y": 50}
                        },
                        {
                            "type": "ellipse",
                            "center": {"x": 0,  "y": 0 },
                            "radius": {"x": 50, "y": 50}
                        }
                    ]
                }
            }
        )),
        pictures_get_by_id(tester_token, 1),
        pictures_get_by_id(tester_token, 2),
        pictures_get_by_id(anon_token, 1),
        pictures_get_all_by_owner_id(tester_token, 1),
        pictures_taps_post(tester_token, 1, Point(0, 0)),
        pictures_taps_post(tester_token, 1, Point(100, 100)),
        pictures_taps_post(tester_token, 1, Point(2 * R, 2 * R)),
        pictures_taps_get_all_by_owner_id(tester_token, 1, 1)
    ]: print_response(response)
