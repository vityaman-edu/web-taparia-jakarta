import { Canvas } from './draw/canvas.js';
import { Vector } from "./picture/figure/vector.js";
import { Api } from "./api/api.js";
import { Utility } from './api/utility.js';
import { FigureFactory } from './picture/figure/figureFactory.js';
import { TabLayout } from './tabLayout.js';
import { Element } from './element.js';
import { PictureField } from './pictureField.js';
import { ResultTable } from './resultTable.js';
import { PictureExplorer } from './pictureExplorer.js';
import { AccessToken } from './api/dto/accessToken.js';

enum UserState {
    TAP_ADD,
    TAP_REMOVE,
}

function setUserState(evt: MouseEvent, state: UserState) {
    Array.from(document.getElementsByClassName("state-switch-button"))
        .map(e => e as HTMLElement)
        .forEach(e =>
            e.className = e.className.replace(" active", "")
        );
    (evt.currentTarget as HTMLElement).className += " active";
    if (state == UserState.TAP_ADD) {
        canvas.setMouseClickListener(pos => {
            if (!pictureField.isEmpty()) {
                pictureField.tap(pos)
            }
        })
    } else {
        throw new Error(`State ${state} not handled`);
    }
}

function redirectToLogin() {
    location.href = "../login.xhtml";
}

const apiUrl = '../..'
const timeout = 1500
const token = AccessToken.loadFromCookies()
if (token === null) {
    redirectToLogin()
}
const api = new Api(apiUrl, timeout, token, (resp, status, error) => {
    alertify.error(JSON.stringify(JSON.parse(resp.responseText), null, 2))
    if (resp.status === 401) {
        redirectToLogin()
    }
})

const canvasCenter = new Vector(250, 250)
const canvas = new Canvas(Element.canvas, canvasCenter)

const tabs = new TabLayout(Element.inputArea, "tabcontent", "tablinks")
Element.Button.Tab.Field.enter.onclick = e => 
    tabs.setActiveTab(e, "tap")
Element.Button.Tab.Explorer.enter.onclick = e => 
    tabs.setActiveTab(e, "edit")
Element.Button.Tab.Field.Tap.add.click()

const resultTable = new ResultTable(Element.table)

const pictureField = new PictureField(canvas, resultTable, api)

let pictureExplorer: PictureExplorer
pictureExplorer = new PictureExplorer(api, token.userId)
Element.Button.Tab.Explorer.Picture.list.onclick = _e => {
    var text = ""
    pictureExplorer.list().forEach(pic => 
        text += pic.name + ', '
    )
    if (text === "") text = "empty"
    alertify.success(text)
}
Element.Button.Tab.Explorer.Picture.get.onclick = _e => {
    const name = Element.Input.Tab.Explorer.Picture.name.value
    const picture = pictureExplorer.getByName(name)
    if (picture) {
        pictureField.load(picture)
        Element.Input.Tab.Explorer.Picture.json.value = 
            JSON.stringify(picture.figure, null, 2)
    } else {
        alertify.error("Picture not found, try refresh list or create it")
    }
}
Element.Button.Tab.Explorer.Picture.create.onclick = _e => {
    const name = Element.Input.Tab.Explorer.Picture.name.value;
    const json = Element.Input.Tab.Explorer.Picture.json.value;
    try {
        const figure = 
            FigureFactory.fromJson(
                Utility.deepConvertToMap(
                    JSON.parse(json)
                )
            )
        pictureExplorer
            .save(name, figure)
            .then(pictureField.load.bind(pictureField))
        Element.Input.Tab.Explorer.Picture.json.value = 
            JSON.stringify(figure, null, 2)
    } catch (error) {
        const message = (error as Error).message
        alertify.error(message)
    }
}

Element.Button.Tab.Field.Tap.add.onclick = e =>
    setUserState(e, UserState.TAP_ADD)
Element.Button.Tab.Field.Tap.add.click()

window.onerror = (message, _url, _line) => {
    alertify.error(message.toString())
    return false
}
