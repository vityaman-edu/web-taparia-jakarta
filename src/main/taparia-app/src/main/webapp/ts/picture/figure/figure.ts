import { Drawable } from "../../draw/drawable.js";

export abstract class Figure implements Drawable {
    protected constructor(
        public readonly type: string
    ) {
    }

    abstract draw(ctx: CanvasRenderingContext2D): void
}
