import { Figure } from "./picture/figure/figure.js";
import { TapResult } from "./api/dto/tapResult.js";

export class ResultTable {
    private results: Array<TapResult>

    constructor(private readonly table: HTMLTableElement) {
        this.clean()   
    }

    load(results: TapResult[]) {
        this.results = results
        this.redraw()
    }

    add(result: TapResult) {
        this.results.push(result)
        const row = this.table.insertRow(1)
        row.insertCell(0).innerHTML = result.ownerId.toString()
        row.insertCell(1).innerHTML = result.status.toString()
        row.insertCell(2).innerHTML = result.point.toString()
    }

    removeAllInArea(area: Figure) {
        throw new Error("Not implemented")
    }

    private clean() {
        this.results = []
        this.redraw()
    }

    private redraw() {
        this.table.innerHTML =
            "<tr>\n" +
            "  <th>Owner</th>\n" +
            "  <th>Point</th>\n" +
            "  <th>Result</th>\n" +
            "</tr>"
        this.results.forEach(
            ((r: TapResult) => this.add(r)).bind(this)
        )
    }
}
