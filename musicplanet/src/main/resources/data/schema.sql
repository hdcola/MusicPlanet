CREATE TABLE IF NOT EXISTS users ( username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(100) NOT NULL);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username)
        REFERENCES users (username)
);

CREATE TABLE IF NOT EXISTS artists ( artist_name VARCHAR(100) NOT NULL PRIMARY KEY, artist_id INT NOT NULL);

CREATE TABLE IF NOT EXISTS `tracks` (
  `track_id` INT NOT NULL,
  `track_name` VARCHAR(300) NULL,
  `album_id` INT NULL,
  `artist_id` INT NULL,
  PRIMARY KEY (`track_id`));