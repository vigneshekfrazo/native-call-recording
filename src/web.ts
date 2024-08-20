import { WebPlugin } from '@capacitor/core';

import type { ExamplePlugin } from './definitions';

export class ExampleWeb extends WebPlugin implements ExamplePlugin {
  async initiateCall(options: { phoneNumber: string }): Promise<void> {
    console.warn('This feature is not available on the web.');
  }
}
