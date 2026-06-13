import { useState } from 'react';
import { Box, Button, Container, TextField, Typography, Paper, CircularProgress, Alert } from '@mui/material';
import type { IApiSuccessResponse } from '../response/IApiSuccessResponse';

export interface IHomeProps { }

const Home = (props: IHomeProps) => {
    const [originalUrl, setOriginalUrl] = useState('');
    const [shortUrl, setShortUrl] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');

    const handleShorten = async () => {
        if (!originalUrl) return;

        setIsLoading(true);
        setError('');
        setShortUrl('');

        try {
            const response: Response = await fetch('/shorten', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                // Sending both 'url' and 'originalUrl' to cover common API payload names
                body: JSON.stringify({ url: originalUrl, originalUrl: originalUrl }),
            });

            if (!response.ok) {
                throw new Error('Failed to shorten URL. Server responded with ' + response.status);
            }

            // The server might return a JSON object or just a plain string
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                const data: IApiSuccessResponse = await response.json();
                setShortUrl(data.payload.shortUrl);
            } else {
                const textData = await response.text();
                setShortUrl(textData);
            }
        } catch (err) {
            console.error(err);
            setError('An error occurred while shortening the URL. Make sure the backend is running and CORS is enabled.');
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
                        onChange={(e) => setOriginalUrl(e.target.value)}
                        placeholder="https://example.com/very-long-url"
                        sx={{ mb: 2 }}
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

                {error && (
                    <Alert severity="error" sx={{ mt: 3 }}>
                        {error}
                    </Alert>
                )}

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