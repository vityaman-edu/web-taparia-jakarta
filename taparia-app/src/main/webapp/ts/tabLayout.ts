
export class TabLayout {
    constructor(
        private readonly root: HTMLElement,
        private readonly contentSelector: string,
        private readonly tabLinkSelector: string
    ) {}

    setActiveTab(evt: MouseEvent, linkId: string) {
        Array.from(this.root.getElementsByClassName(this.contentSelector))
            .map(e => e as HTMLElement)
            .forEach(e => e.style.display = "none");
        Array.from(this.root.getElementsByClassName(this.tabLinkSelector))
            .map(e => e as HTMLElement)
            .forEach(e => e.className = e.className.replace(" active", ""));
        document.getElementById(linkId).style.display = "block";
        (evt.currentTarget as HTMLElement).className += " active";
    }
}