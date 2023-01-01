import {Vector} from "../picture/figure/vector.js";
import {Ellipse} from "../picture/figure/ellipse.js";
import {Colored} from "../picture/figure/colored.js";
import {Segment} from "./segment.js";
import {Drawable} from "./drawable.js";

const INF = 999

export class CoordinatesDrawable implements Drawable {
    private origin = Drawable.union([
        new Segment(
            new Vector(-INF, 0),
            new Vector(INF, 0),
            2,
            '#A00'
        ),
        new Segment(
            new Vector(0, -INF),
            new Vector(0, INF),
            2,
            '#A00'
        ),
        new Colored('#00F', new Ellipse(
            new Vector(0, 0),
            new Vector(3, 3)
        ))
    ])

    draw(ctx: CanvasRenderingContext2D): void {
        this.origin.draw(ctx);
    }
}