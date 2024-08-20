export interface ExamplePlugin {
    initiateCall(options: {
        phoneNumber: string;
    }): Promise<void>;
}
