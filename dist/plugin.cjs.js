'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const Example = core.registerPlugin('Example', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.ExampleWeb()),
});

class ExampleWeb extends core.WebPlugin {
    async initiateCall(_options) {
        console.warn('This feature is not available on the web.');
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    ExampleWeb: ExampleWeb
});

exports.Example = Example;
//# sourceMappingURL=plugin.cjs.js.map
