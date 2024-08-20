import { WebPlugin } from '@capacitor/core';
export class ExampleWeb extends WebPlugin {
    async initiateCall(_options) {
        console.warn('This feature is not available on the web.');
    }
}
//# sourceMappingURL=web.js.map