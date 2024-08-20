import { WebPlugin } from '@capacitor/core';
import type { ExamplePlugin } from './definitions';
export declare class ExampleWeb extends WebPlugin implements ExamplePlugin {
    initiateCall(_options: {
        phoneNumber: string;
    }): Promise<void>;
}
