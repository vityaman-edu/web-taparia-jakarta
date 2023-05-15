import { Api } from "./api/api.js";
import { Figure } from "./picture/figure/figure.js";
import { Picture } from "./picture/picture.js";

export class PictureExplorer {
    constructor(
        private readonly api: Api,
        private readonly userId: number,
        private picturesByName: Map<string, Picture> = new Map()
    ) {
        this.refresh()
    }

    list(): Array<Picture> {
        return [...this.picturesByName.values()];
    }

    refresh(): void {
        this.api.pictures
            .getAllByOwnerId(this.userId)
            .then(pictures => {
                this.picturesByName.clear()
                pictures.forEach(p => 
                    this.picturesByName.set(p.name, p)
                )
            })
    }

    getByName(name: string): Picture {
        return this.picturesByName.get(name);
    }

    save(name: string, figure: Figure): Promise<Picture> {
        const userId = this.userId
        return this.api.pictures
            .post(name, figure)
            .then(pictureId => {
                const picture =
                    new Picture(
                        pictureId,
                        userId,
                        name,
                        figure
                    )
                this.picturesByName.set(name, picture)
                return picture
            })       
    }
}
