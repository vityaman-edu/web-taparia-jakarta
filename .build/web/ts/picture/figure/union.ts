import {Figure} from "./figure.js";
import {FigureAggregator} from "./figureAggregator.js";

export class Union extends FigureAggregator {
    constructor(
        children: Array<Figure>
    ) {
        super("union", children);
    }
}
