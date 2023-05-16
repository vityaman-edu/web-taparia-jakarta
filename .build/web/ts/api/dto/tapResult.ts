import {Vector} from "../../picture/figure/vector.js";

export class TapResult {
    constructor(
        public readonly id: number,
        public readonly ownerId: number,
        public readonly pictureId: number,
        public readonly point: Vector,
        public readonly status: string
    ) {}
}

export namespace TapResult {
    export function fromJson(json: Map<string, any>): TapResult {
        return new TapResult(
            json.get('id'),
            json.get('owner_id'),
            json.get('picture_id'),
            Vector.fromJson(json.get('point')),
            json.get('status')
        )
    }
}