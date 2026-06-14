export interface IApiSuccessResponse<T> {
    success: boolean;
    message: string;
    payload: T;
}

export interface IShortenUrlPayload {
    shortUrl: string;
}