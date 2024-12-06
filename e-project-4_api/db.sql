create database epj_4;
use epj_4;

-- Create `users` table
CREATE TABLE users (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(60) NOT NULL,
    phone VARCHAR(10),
    email VARCHAR(100),
    role VARCHAR(20),
    bio VARCHAR(500),
    dob DATE,
    is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME
);
-- Create `albums` table
CREATE TABLE albums (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    artist_id INT(11),
    image VARCHAR(150),
    release_date DATE,
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (artist_id) REFERENCES users(id) ON DELETE CASCADE
);
-- Create `genres` table
CREATE TABLE genres (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    image VARCHAR(150),
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME
);
-- Create `playlists` table
CREATE TABLE playlists (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    user_id INT(11),
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
-- Create `songs` table
CREATE TABLE songs (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    album_id INT(11),
    audio_path VARCHAR(150),
    like_amount INT(11) ,
    amount INT(11) ,
    lyric_file_path VARCHAR(150),
    is_pending BOOLEAN,
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE
);

-- Create `genre_song` (many-to-many) table
CREATE TABLE genre_song (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    genre_id INT(11),
    song_id INT(11),
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);

-- Create `sub_artist` table (many-to-many)
CREATE TABLE sub_artist (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    artist_id INT(11),
    song_id INT(11),
    is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (artist_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);
-- Create `playlist_song` table (many-to-many)
CREATE TABLE playlist_song (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    playlist_id INT(11),
    song_id INT(11),
    is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);

