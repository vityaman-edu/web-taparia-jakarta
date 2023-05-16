import {Vector} from "./vector.js";
import {Figure} from "./figure.js";

export class Polygon extends Figure {
    constructor(
        public readonly points: Array<Vector>
    ) {
        super("polygon");
    }

    draw(ctx: CanvasRenderingContext2D): void {
        const points = this.points;
        ctx.beginPath();
        ctx.moveTo(points[0].x, points[0].y)
        for (let i = 1; i < points.length; i++) {
            ctx.lineTo(points[i].x, points[i].y);
        }
        ctx.closePath();
        ctx.fill();
        ctx.stroke()
    }
}

export namespace Polygon {
    export function fromJson(json: Map<string, any>): Polygon {
        return new Polygon(
            (json.get('points') as Array<Map<string, any>>)
                .map(Vector.fromJson)
        );
    }
}