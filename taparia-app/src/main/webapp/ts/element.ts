export namespace Element {
    const element = (id: string) => document.getElementById(id);
    const button = (id: string) => element(id) as HTMLButtonElement
    const input = (id: string) => element(id) as HTMLInputElement
    export namespace Button {
        export namespace Tab {
            export namespace Field {
                export const enter = button("tab-button-tap")
                export namespace Tap {
                    export const add = button("button-tap-add") 
                }
            }
            export namespace Explorer {
                export const enter = button("tab-button-edit")
                export namespace Picture {
                    export const create = button("create-picture")
                    export const list = button("list-pictures")
                    export const get = button("get-picture")
                }
            }
        }
    }
    export namespace Input.Tab.Explorer.Picture {
        export const name = input("picture-name")
        export const json = input("picture-data")
    }
    export const inputArea = element("input-area") as HTMLElement
    export const canvas = element("canvas") as HTMLCanvasElement
    export const table = element("result-table") as HTMLTableElement
}
