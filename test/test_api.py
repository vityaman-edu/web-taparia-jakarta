from test_lib import *

R = 100

tester_username = "tester"
tester_password = "qwerty123"
tester_credentials = UserCredentials(tester_username, tester_password)

tester_token = register_user(tester_credentials)

anon_token = register_user(UserCredentials("anon", "lalala"))

for test in [
    TestAction(
        lambda: users_get_by_name(tester_username),
        ResponseExpectation(
            status_code = 200,
        )
    ),
    TestAction(
        lambda: pictures_post(tester_token, PictureDraft(
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
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_post(tester_token, PictureDraft(
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
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_get_by_id(tester_token, 1),
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_get_by_id(tester_token, 2),
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_get_by_id(anon_token, 1),
        ResponseExpectation(
            status_code = 401
        )
    ),
    TestAction(
        lambda: pictures_get_all_by_owner_id(tester_token, 1),
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_taps_post(tester_token, 1, Point(0, 0)),
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_taps_post(tester_token, 1, Point(100, 100)),
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_taps_post(tester_token, 1, Point(2 * R, 2 * R)),
        ResponseExpectation(
            status_code = 200
        )
    ),
    TestAction(
        lambda: pictures_taps_get_all_by_owner_id(tester_token, 1, 1),
        ResponseExpectation(
            status_code = 200
        )
    )
]:
    response = test.action()
    print_response(response)
    assert response.status_code == test.expected.status_code
