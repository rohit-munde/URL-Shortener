export interface IShortenUrlRequest {
    url: string;
    originalUrl?: string; // Some backends might use either field name
}
