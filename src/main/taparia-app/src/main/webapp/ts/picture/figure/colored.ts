import { Figure } from "./figure.js";

export class Colored extends Figure {
    constructor(
        public readonly color: string,
        public readonly child: Figure
    ) {
        super("colored");
    }

    draw(ctx: CanvasRenderingContext2D): void {
        const pastFillStyle = ctx.fillStyle
        ctx.fillStyle = this.color;
        this.child.draw(ctx);
        ctx.fillStyle = pastFillStyle
    }
}
