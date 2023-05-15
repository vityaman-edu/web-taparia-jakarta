import {FigureFactory} from "./figure/figureFactory.js";
import {Figure} from "./figure/figure.js";

export class Picture {
    constructor(
        public readonly id: number,
        public readonly ownerId: number,
        public readonly name: string,
        public readonly figure: Figure
    ) {
    }
}

export namespace Picture {
    export function fromJson(json: Map<string, any>): Picture {
        return new Picture(
            json.get('id'),
            json.get('owner_id'),
            json.get('name'),
            FigureFactory.fromJson(json.get('data') as Map<string, any>)
        );
    }
}