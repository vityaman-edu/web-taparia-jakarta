import { Api } from "./api/api.js";
import { TapResult } from "./api/dto/tapResult.js";
import { Canvas } from "./draw/canvas.js";
import { CoordinatesDrawable } from "./draw/coordinatesDrawable.js";
import { Colored } from "./picture/figure/colored.js";
import { Ellipse } from "./picture/figure/ellipse.js";
import { Vector } from "./picture/figure/vector.js";
import { Picture } from "./picture/picture.js";
import { ResultTable } from "./resultTable.js";

export class PictureField {
    constructor(
        private readonly canvas: Canvas,
        private readonly table: ResultTable,
        private readonly api: Api,
        private picture: Picture = null,
        private readonly coordinates = new CoordinatesDrawable(),
        private readonly dotR = 4
    ) {
        this.redraw()
    }

    tap(point: Vector) {
        const table = this.table
        this.api.picturesTaps
            .post(this.picture.id, point)
            .then(((tap: TapResult) => {
                this.drawTap(tap)
                table.add(tap)
            }).bind(this))
    }

    load(picture: Picture) {
        this.picture = picture
        this.api.picturesTaps
            .getAllByOwnerId(picture.id, picture.ownerId)
            .then(((results: TapResult[]) => {
                this.redraw()
                this.table.load(results)
                results.forEach(r => this.drawTap(r))
            }).bind(this))   
    }

    isEmpty() {
        return this.picture == null
    }

    private drawTap(tap: TapResult) {
        this.canvas.draw(this.dot(tap.point, this.colorOf(tap.status)))
    }

    private clear() {
        this.canvas.clear()
        this.canvas.draw(this.coordinates)
    }

    private redraw() {
        this.clear()
        if (!this.isEmpty()) {
            this.canvas.draw(this.picture.figure)
        }
    }

    private dot(pos: Vector, color: string) {
        return new Colored(color, new Ellipse(
            new Vector(pos.x, pos.y),
            new Vector(this.dotR, this.dotR)
        ));
    }

    private colorOf(status: string) {
        return (
            (status == 'HIT') 
                ? ("#0F0") 
                : ("#F00")
        )
    }
}
