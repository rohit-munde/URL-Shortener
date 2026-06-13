export interface IApiSuccessResponse {
    success: boolean;
    message: string;
    payload: IShortenUrlPayload;
}

export interface IShortenUrlPayload {
    shortUrl: string;
}