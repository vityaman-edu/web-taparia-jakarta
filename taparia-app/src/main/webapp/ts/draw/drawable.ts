
export interface Drawable {
    draw(ctx: CanvasRenderingContext2D): void
}

export namespace Drawable {
    export function union(drawables: Array<Drawable>): Drawable {
        return {
            draw(ctx: CanvasRenderingContext2D): void {
                drawables.forEach(d => d.draw(ctx))
            }
        }
    }
}