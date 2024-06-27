CREATE TABLE IF NOT EXISTS users ( username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(100) NOT NULL, enabled BOOLEAN NOT NULL, spotify_access_token VARCHAR(255), spotify_refresh_token VARCHAR(255), spotify_token_expires_at DATETIME );

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username)
        REFERENCES users (username)
);
