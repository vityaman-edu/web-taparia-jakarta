export class Vector {
    constructor(
        public readonly x: number,
        public readonly y: number,
    ) {
    }

    equals(other: Vector): boolean {
        return this.x === other.x
            && this.y === other.y;
    }

    toString(): string {
        return `(${this.x}, ${this.y})`;
    }
}

export namespace Vector {
    export function fromJson(json: Map<string, any>) {
        return new Vector(
            json.get('x') as number,
            json.get('y') as number
        );
    }
}
