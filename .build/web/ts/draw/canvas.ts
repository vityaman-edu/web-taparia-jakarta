import {Vector} from "../picture/figure/vector.js";
import {Polygon} from "../picture/figure/polygon.js";
import {Drawable} from "./drawable.js";
import { Colored } from "../picture/figure/colored.js";

type MouseEventCallback = (mousePosition: Vector) => void

export class Canvas {
    constructor(
        private readonly canvas: HTMLCanvasElement,
        private readonly origin: Vector
    ) {
    }

    draw(drawable: Drawable): Canvas {
        const ctx = this.canvas.getContext("2d")
        ctx.save();
        ctx.translate(this.origin.x, this.origin.y)
        ctx.scale(1, -1)
        ctx.fillStyle = "#444"
        ctx.lineWidth = 2
        drawable.draw(ctx)
        ctx.restore()
        return this;
    }

    clear() {
        const INF = 9999
        this.draw(new Colored("#FFFFFF",new Polygon([
            new Vector(-INF, -INF),
            new Vector(INF, -INF),
            new Vector(INF, INF),
            new Vector(-INF, INF)
        ])));
    }

    setMouseClickListener(callback: MouseEventCallback): Canvas {
        this.canvas.onclick = ((e: MouseEvent) => {
            const mousePosition = new Vector(e.clientX, e.clientY)
            callback(this.translate(mousePosition))
        }).bind(this)
        return this;
    }

    private translate(point: Vector) {
        const topLeftCorner = this.topLeftCorner()
        return new Vector(
            point.x - topLeftCorner.x - this.origin.x,
            -(point.y - topLeftCorner.y - this.origin.y)
        )
    }

    private topLeftCorner(): Vector {
        const rectangle = this.canvas.getBoundingClientRect();
        return new Vector(rectangle.left, rectangle.top)
    }
}
