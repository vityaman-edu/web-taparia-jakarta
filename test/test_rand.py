from test_lib import *
import random

def random_point() -> Point:
    return Point(
        x = random.randint(0, 2 * R), 
        y = random.randint(0, 2 * R)
    )

print('Welcome to Taparia.Tank!')

R = 100

usernames = input("Enter usernames: ").split()

credentials = list(map(lambda name: UserCredentials(name, name), usernames))

print('[TANK] Credentials:')
list(map(print, credentials))

iterations_count = int(input("Enter iterations count: "))
print(f'[TANK] Iterations count is {iterations_count}')

tokens = list(map(register_user, credentials))
print('[TANK] Access Tokens:')
list(map(print, tokens))

picture_templates = [
    {
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
    },
    {
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
]

picture_ids_by_owner = { k: [] for k in range(len(tokens)) }

picture_name_seq = 1

stat = {
    'picture_create': 0,
    'picture_get': 0,
    'picture_get_all': 0,
    'tap_post': 0,
    'tap_get': 0,
}

def picture_create():
    global picture_name_seq
    stat['picture_create'] += 1
    i = random.randint(0, len(tokens) - 1)
    print(f'[TANK] pictures_post({i})')
    response = pictures_post(tokens[i], PictureDraft(
        name = "picture" + str(picture_name_seq),
        data = random.choice(picture_templates)
    ))
    print_response(response)
    assert response.status_code == 200

    picture_ids_by_owner[i].append(int(response.origin.json().get('picture_id')))
    picture_name_seq += 1


def picture_get():
    stat['picture_get'] += 1
    i = random.randint(0, len(tokens) - 1)
    pic_ids = picture_ids_by_owner[i]
    if len(pic_ids) > 0:
        pic_id = random.choice(pic_ids)
        print(f'[TANK] pictures_get_by_id({i}, {pic_id})')
        response = pictures_get_by_id(tokens[i], pic_id)
        print_response(response)
        assert response.status_code == 200


def picture_get_all():
    stat['picture_get_all'] += 1
    i = random.randint(0, len(tokens) - 1)
    print(f'[TANK] pictures_get_all_by_owner_id({i})')
    response = pictures_get_all_by_owner_id(tokens[i], tokens[i].user_id)
    print_response(response)
    assert response.status_code == 200


def tap_post():
    stat['tap_post'] += 1
    i = random.randint(0, len(tokens) - 1)
    pic_ids = picture_ids_by_owner[i]
    if len(pic_ids) > 0:
        pic_id = random.choice(pic_ids)
        print(f'[TANK] pictures_taps_post({i}, {pic_id})')
        response = pictures_taps_post(tokens[i], pic_id, random_point())
        print_response(response)
        assert response.status_code == 200

def tap_get():
    stat['tap_get'] += 1
    i = random.randint(0, len(tokens) - 1)
    pic_ids = picture_ids_by_owner[i]
    if len(pic_ids) > 0:
        pic_id = random.choice(pic_ids)
        print(f'[TANK] pictures_taps_get_all_by_owner_id({i}, {pic_id})')
        response = pictures_taps_get_all_by_owner_id(
            tokens[i], pic_id, tokens[i].user_id
        )
        print_response(response)
        assert response.status_code == 200


actions = [
    picture_create,
    picture_get,
    picture_get_all,
    tap_post,
    tap_get,
]

for i in range(iterations_count):
    random.choice(actions)()
    if i % 5 == 0:
        print(f'[TANK] Progress: {i + 1}/{iterations_count}')
print(stat)