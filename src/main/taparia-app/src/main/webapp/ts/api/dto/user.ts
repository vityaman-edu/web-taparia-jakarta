
export class User {
    constructor(
        public readonly id: number,
        public readonly name: string
    ) {}
}

export namespace User {
    export function fromJson(json: Map<string, any>) {
        return new User(
            json.get('id'), 
            json.get('name')
        );
    }
}