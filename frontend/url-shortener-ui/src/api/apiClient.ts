import type { IApiErrorResponse } from '../response/IApiErrorResponse';
import { eventBus } from '../utils/eventBus';

export class ApiError extends Error {
    public status: number;
    public data: unknown;

    constructor(message: string, status: number, data?: unknown) {
        super(message);
        this.status = status;
        this.data = data;
        this.name = 'ApiError';
    }
}

async function handleResponse<T>(response: Response): Promise<T> {
    const contentType = response.headers.get('content-type');
    const isJson = contentType && contentType.includes('application/json');

    let data: unknown;
    if (isJson) {
        data = await response.json();
    } else {
        const text = await response.text();
        data = text ? text : null;
    }

    if (!response.ok) {
        const errorData = isJson ? data as IApiErrorResponse : {
            timeStamp: new Date().toISOString(),
            status: response.status,
            error: response.statusText,
            message: typeof data === 'string' ? data : response.statusText,
            path: ''
        } as IApiErrorResponse;
        const errorMessage = errorData.message || errorData.error || 'API request failed';
        eventBus.emit('API_ERROR', errorMessage);
        throw new ApiError(errorMessage, response.status, errorData);
    }

    return data as T;
}

export const apiClient = {
    get: async <T>(url: string, headers?: HeadersInit): Promise<T> => {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                ...headers,
            },
        });
        return handleResponse<T>(response);
    },

    post: async <T, D = unknown>(url: string, body: D, headers?: HeadersInit): Promise<T> => {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                ...headers,
            },
            body: JSON.stringify(body),
        });
        return handleResponse<T>(response);
    },

    // Add put, delete etc. as needed in the future
};
