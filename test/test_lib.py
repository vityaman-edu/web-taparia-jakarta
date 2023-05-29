from typing import NamedTuple
import json
import requests

base_url = "http://ya.ru/"
path = lambda suffix: base_url + suffix

class UserCredentials(NamedTuple):
    username: str
    password: str

class AccessToken(NamedTuple):
    user_id: int
    secret: str

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

class PictureDraft(NamedTuple):
    name: str
    data: dict

class Point(NamedTuple):
    x: int
    y: int

class Response(NamedTuple):
    origin: requests.Response

    @property
    def status_code(self) -> int:
        return self.origin.status_code


def print_response(r: Response):
    r = r.origin
    print("request ", r.request.method, r.request.url)
    print("status_code =", r.status_code)
    print("headers =", r.headers)
    try:
        obj = json.loads(r.text)
        print("data =", json.dumps(obj, indent=4))
    except Exception:
        print("data =", r.text)


def users_post(c: UserCredentials) -> Response:
    return Response(requests.post(
        url = path("users"),
        headers = credentials_headers(c)
    ))


def users_get_by_name(username: str):
    return Response(requests.get(
        url = path(f"users?name={username}")
    ))

def pictures_post(t: AccessToken, draft: PictureDraft):
    return Response(requests.post(
        url = path("pictures"),
        headers = access_token_headers(t),
        json = {
            "name": draft.name,
            "data": draft.data
        }
    ))

def pictures_get_by_id(t: AccessToken, picture_id: int):
    return Response(requests.get(
        url = path(f"pictures/{picture_id}"),
        headers = access_token_headers(t)
    ))


def pictures_get_all_by_owner_id(t: AccessToken, owner_id: int):
    return Response(requests.get(
        url = path(f"pictures?owner_id={owner_id}"),
        headers = access_token_headers(t)
    ))


def pictures_taps_post(t: AccessToken, picture_id: int, point: Point):
    return Response(requests.post(
        url = path(f"pictures/{picture_id}/taps"),
        headers = access_token_headers(t),
        json = {
            "x": point.x,
            "y": point.y
        }
    ))


def pictures_taps_get_all_by_owner_id(
    t: AccessToken, picture_id: int, owner_id: int
) -> Response: 
    return Response(requests.get(
        url = path(f"pictures/{picture_id}/taps?owner_id={owner_id}"),
        headers = access_token_headers(t)
    ))

def auth_access_tokens_get(c: UserCredentials) -> Response:
    return Response(requests.get(
        url = path(f"auth/access_tokens"),
        headers = credentials_headers(c)
    ))

def register_user(credentials: UserCredentials) -> AccessToken:
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
