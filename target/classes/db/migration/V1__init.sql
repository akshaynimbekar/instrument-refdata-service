
#will use this with flyway

CREATE TABLE instrument (
    id BIGSERIAL PRIMARY KEY,
    isin VARCHAR(12) NOT NULL UNIQUE,
    cusip VARCHAR(9) UNIQUE,
    ticker VARCHAR(10),
    exchange VARCHAR(50),
    currency VARCHAR(3),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
