
export class AccessToken {
    constructor(
        public readonly userId: number,
        public readonly token: string
    ) {}

    toHeaders(): any {
        return {
            'X-Auth-User-ID': this.userId,
            'X-Auth-Access-Token': this.token
        };
    }
}

export namespace AccessToken {
    export function loadFromCookies(): AccessToken | null {
        const userId = getCookieByName('user_id')
        const accessToken = getCookieByName('access_token')
        if (userId === null || accessToken === null) {
            return null;
        }
        return new AccessToken(Number.parseInt(userId), accessToken)
    }

    // https://stackoverflow.com/questions/10730362/get-cookie-by-name
    function getCookieByName(name: string): string | null {
        const value = `; ${document.cookie}`
        const parts = value.split(`; ${name}=`)
        if (parts.length === 2) {
            return parts.pop().split(';').shift()
        } 
        return null;
    }
}