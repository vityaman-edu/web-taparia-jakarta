import {Figure} from "./figure.js";

export abstract class FigureAggregator extends Figure {
    protected constructor(
        type: string,
        public readonly children: Array<Figure>
    ) {
        super(type);
    }

    draw(ctx: CanvasRenderingContext2D) {
        this.children.forEach(child => child.draw(ctx));
    }
}