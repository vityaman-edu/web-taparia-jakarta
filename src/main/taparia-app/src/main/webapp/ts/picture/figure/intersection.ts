import {Figure} from "./figure.js";
import {FigureAggregator} from "./figureAggregator.js";

export class Intersection extends FigureAggregator {
    constructor(
        children: Array<Figure>
    ) {
        super("intersection", children);
    }

}