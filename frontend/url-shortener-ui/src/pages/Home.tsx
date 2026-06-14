import { useState } from 'react';
import { Box, Button, Container, TextField, Typography, Paper, CircularProgress } from '@mui/material';
import type { IApiSuccessResponse, IShortenUrlPayload } from '../response/IApiSuccessResponse';
import type { IShortenUrlRequest } from '../request/IShortenUrlRequest';
import { apiClient, ApiError } from '../api/apiClient';
import type { IApiErrorResponse } from '../response/IApiErrorResponse';
const Home = () => {
    const [originalUrl, setOriginalUrl] = useState('');
    const [shortUrl, setShortUrl] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [validationErrors, setValidationErrors] = useState<Record<string, string>>({});

    const handleShorten = async () => {
        if (!originalUrl) return;

        setIsLoading(true);
        setValidationErrors({});
        setShortUrl('');

        try {
            const requestPayload: IShortenUrlRequest = { url: originalUrl, originalUrl: originalUrl };
            // The backend might return a plain string or a standard JSON payload
            const data = await apiClient.post<IApiSuccessResponse<IShortenUrlPayload>>('/shorten', requestPayload);

            if (data.success && data.payload) setShortUrl(data.payload.shortUrl);

        } catch (err: unknown) {
            console.error('Handled globally via interceptor', err);
            if (err instanceof ApiError) {
                const apiData = err.data as IApiErrorResponse;
                if (apiData?.validationErrors) {
                    setValidationErrors(apiData.validationErrors);
                }
            }
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Container maxWidth="sm" className="app-container">
            <Paper elevation={3} className="app-paper">
                <Typography variant="h4" component="h1" gutterBottom align="center" className="title">
                    URL Shortener
                </Typography>

                <Box className="input-section">
                    <TextField
                        fullWidth
                        label="Original URL"
                        variant="outlined"
                        value={originalUrl}
                        onChange={(e) => {
                            setOriginalUrl(e.target.value);
                            // Clear error as user types
                            if (validationErrors['url'] || validationErrors['originalUrl']) {
                                setValidationErrors({});
                            }
                        }}
                        placeholder="https://example.com/very-long-url"
                        sx={{ mb: 2 }}
                        error={!!validationErrors['url'] || !!validationErrors['originalUrl']}
                        helperText={validationErrors['url'] || validationErrors['originalUrl']}
                    />

                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        size="large"
                        onClick={handleShorten}
                        disabled={isLoading || !originalUrl}
                    >
                        {isLoading ? <CircularProgress size={24} /> : 'Short URL'}
                    </Button>
                </Box>



                {shortUrl && (
                    <Box className="result-section" sx={{ mt: 4, p: 2, bgcolor: '#f5f5f5', borderRadius: 1 }}>
                        <Typography variant="subtitle1" color="textSecondary">
                            Short URL =
                        </Typography>
                        <Typography variant="h6" color="primary" sx={{ wordBreak: 'break-all' }}>
                            <a
                                href={shortUrl.startsWith('http') ? shortUrl : `/r/${shortUrl}`}
                                target="_blank"
                                rel="noopener noreferrer"
                                style={{ textDecoration: 'none', color: '#1976d2' }}
                            >
                                {shortUrl.startsWith('http') ? shortUrl : `${window.location.origin}/r/${shortUrl}`}
                            </a>
                        </Typography>
                    </Box>
                )}
            </Paper>
        </Container>
    );
};

export default Home;