import { Picture } from "../picture/picture.js"
import { Utility } from "./utility.js";
import { User } from "./dto/user.js";
import { Figure } from "../picture/figure/figure.js";
import { Vector } from "../picture/figure/vector.js";
import { TapResult } from "./dto/tapResult.js";
import { AccessToken } from "./dto/accessToken.js";

const GET = 'GET'
const POST = 'POST'

export class Api {
    constructor(
        private readonly host: string,
        private readonly timeout: number,
        private readonly token: AccessToken,
        private readonly onError: (
            request: JQuery.jqXHR<any>, 
            status: JQuery.Ajax.ErrorTextStatus,
            error: string
        ) => void
    ) {
    }

    ops = new class {
        constructor(private readonly api: Api) {}

        ping(): Promise<void> {
            return new Promise((resolve, reject) => $.ajax({
                type: GET,
                url: this.path('/ping'),
                timeout: this.api.timeout,
                success: resolve,
                error: (request, status, error) => {
                    this.api.onError(request, status, error)
                    reject(...[request, status, error])
                }
            }));
        }

        path(suffix: string): string {
            return `${this.api.path(`/ops/${suffix}`)}`
        }
    }(this);

    users = new class {
        constructor(private readonly api: Api) {}

        getByName(username: string): Promise<User> {
            return new Promise((resolve, reject) => $.ajax({
                type: GET,
                url: this.path(`?name=${username}`),
                timeout: this.api.timeout,
                success: (data: object) => {
                    const json = Utility.deepConvertToMap(data)
                    const user = User.fromJson(json)
                    resolve(user)
                },
                error: (request, status, error) => {
                    this.api.onError(request, status, error)
                    reject(...[request, status, error])
                }
            }));
        }

        path(suffix: string): string {
            return `${this.api.path(`/users${suffix}`)}`
        }
    }(this);

    pictures = new class {
        constructor(private readonly api: Api) {}

        post(name: string, data: Figure): Promise<number> {
            return new Promise((resolve, reject) => $.ajax({
                type: POST,
                url: this.path(''),
                timeout: this.api.timeout,
                headers: this.api.token.toHeaders(),
                contentType: "application/json",
                data: JSON.stringify({
                    'name': name,
                    'data': data
                }),
                success: (data: object) => {
                    const json = Utility.deepConvertToMap(data)
                    resolve(json.get('picture_id'))
                },
                error: (response, status, error) => {
                    this.api.onError(response, status, error)
                    reject(...[response, status, error])
                }
            }));
        }

        getById(pictureId: number): Promise<Picture> {
            return new Promise((resolve, reject) => $.ajax({
                type: GET,
                url: this.path(`/${pictureId}`),
                timeout: this.api.timeout,
                headers: this.api.token.toHeaders(),
                success: (data: object) => {
                    const json = Utility.deepConvertToMap(data)
                    const picture = Picture.fromJson(json)
                    resolve(picture)
                },
                error: (response, status, error) => {
                    this.api.onError(response, status, error)
                    reject(...[response, status, error])
                }
            }));
        }

        getAllByOwnerId(ownerId: number): Promise<Array<Picture>> {
            return new Promise((resolve, reject) => $.ajax({
                type: GET,
                url: this.path(`?owner_id=${ownerId}`),
                timeout: this.api.timeout,
                headers: this.api.token.toHeaders(),
                success: (data: object) => {
                    const pictures = $
                        .makeArray(data as ArrayLike<any>)
                        .map(Utility.deepConvertToMap)
                        .map(Picture.fromJson)
                    resolve(pictures)
                },
                error: (response, status, error) => {
                    this.api.onError(response, status, error)
                    reject(...[response, status, error])
                }
            }));
        }

        path(suffix: string): string {
            return `${this.api.path(`/pictures${suffix}`)}`
        }
    }(this);

    picturesTaps = new class {
        constructor(private readonly api: Api) {}

        post(pictureId: number, point: Vector): Promise<TapResult> {
            return new Promise((resolve, reject) => $.ajax({
                type: POST,
                url: this.path(pictureId, ''),
                timeout: this.api.timeout,
                headers: this.api.token.toHeaders(),
                contentType: "application/json",
                data: JSON.stringify({
                    'x': point.x,
                    'y': point.y,
                }),
                success: (data: object) => {
                    const json = Utility.deepConvertToMap(data)
                    resolve(TapResult.fromJson(json))
                },
                error: (response, status, error) => {
                    this.api.onError(response, status, error)
                    reject(...[response, status, error])
                }
            }))
        }

        getAllByOwnerId(
            pictureId: number, ownerId: number
        ): Promise<Array<TapResult>> {
            return new Promise((resolve, reject) => $.ajax({
                type: GET,
                url: this.path(pictureId, `?owner_id=${ownerId}`),
                timeout: this.api.timeout,
                headers: this.api.token.toHeaders(),
                success: (data: object) => {
                    const results = $
                        .makeArray(data as ArrayLike<any>)
                        .map(Utility.deepConvertToMap)
                        .map(TapResult.fromJson)
                    resolve(results)
                },
                error: (response, status, error) => {
                    this.api.onError(response, status, error)
                    reject(...[response, status, error])
                }
            }))
        }

        path(pictureId: number, suffix: string): string {
            return `${this.api.path(`/pictures/${pictureId}/taps${suffix}`)}`
        }
    }(this);

    private path(suffix: string): string {
        return `${this.host}/api${suffix}`
    }
}
