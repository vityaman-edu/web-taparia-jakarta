import {Vector} from "../picture/figure/vector.js";
import {Drawable} from "./drawable.js";

export class Segment implements Drawable {
    constructor(
        public readonly start: Vector,
        public readonly end: Vector,
        public readonly width: number,
        public readonly color: string
    ) {
    }

    draw(ctx: CanvasRenderingContext2D): void {
        ctx.fillStyle = this.color;
        ctx.lineWidth = this.width;
        ctx.beginPath();
        ctx.moveTo(this.start.x, this.start.y);
        ctx.lineTo(this.end.x, this.end.y);
        ctx.stroke();
        ctx.fill();
    }
}
