import { registerPlugin } from '@capacitor/core';
const Example = registerPlugin('Example', {
    web: () => import('./web').then(m => new m.ExampleWeb()),
});
export * from './definitions';
export { Example };
//# sourceMappingURL=index.js.map