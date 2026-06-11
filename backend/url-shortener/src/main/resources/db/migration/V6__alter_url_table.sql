ALTER TABLE urls
    ADD UNIQUE INDEX idx_url_hash (url_hash);