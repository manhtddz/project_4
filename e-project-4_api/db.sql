create database epj_4;
use epj_4;


CREATE TABLE artists (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    artist_name VARCHAR(100) NOT NULL,
    image VARCHAR(40),
    bio TEXT,
   is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME
);
-- Create `users` table
CREATE TABLE users (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
	full_name VARCHAR(100) NOT NULL,
    avatar VARCHAR(100) NOT NULL,
    password VARCHAR(60) NOT NULL,
    phone VARCHAR(10),
    email VARCHAR(100),
    role VARCHAR(20),
    bio text,
    dob DATE,
    artist_id INT(11),
    is_active Boolean,
    is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE
);
create table subjects(
 id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    image VARCHAR(150),
    description text,
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME
    );
-- Create `albums` table
CREATE TABLE albums (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    artist_id INT(11),
     subject_id INT(11),
    image VARCHAR(150),
	is_released BOOLEAN,
    release_date DATE,
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE,
	FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
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
	idx_playlists_useris_deletedis_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
-- Create `songs` table
CREATE TABLE songs (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    album_id INT(11),
    artist_id INT(11),
    audio_path VARCHAR(150),
    like_amount INT(11) ,
    listen_amount INT(11) ,
    feature_artist VARCHAR(150),
    lyric_file_path VARCHAR(150),
    is_pending BOOLEAN,
     is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE,
	FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE

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
CREATE TABLE favourite_songs (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    user_id INT(11),
    song_id INT(11),
    is_deleted BOOLEAN,
    created_at DATETIME ,
    modified_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
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
-- Bảng artists:
-- Thêm chỉ mục cho cột `artist_name` để tìm kiếm nghệ sĩ theo tên.
CREATE INDEX idx_artists_name ON artists (artist_name);

-- Bảng users:
-- Thêm chỉ mục cho `username` để tìm kiếm người dùng theo tên.
CREATE INDEX idx_users_username ON users (username);
-- Thêm chỉ mục cho `artist_id` để tăng tốc truy vấn với liên kết nghệ sĩ.
CREATE INDEX idx_users_artist ON users (artist_id);

-- Bảng subjects:
-- Thêm chỉ mục cho `title` để tìm kiếm hoặc lọc theo chủ đề.
CREATE INDEX idx_subjects_title ON subjects (title);

-- Bảng albums:
-- Thêm chỉ mục cho `artist_id` để tăng tốc truy vấn liên kết với nghệ sĩ.
CREATE INDEX idx_albums_artist ON albums (artist_id);
-- Thêm chỉ mục cho `subject_id` để tăng tốc truy vấn liên kết với chủ đề.
CREATE INDEX idx_albums_subject ON albums (subject_id);
-- Thêm chỉ mục cho `title` để tìm kiếm album theo tên.
CREATE INDEX idx_albums_title ON albums (title);

-- Bảng genres:
-- Thêm chỉ mục cho `title` để tìm kiếm thể loại.
CREATE INDEX idx_genres_title ON genres (title);

-- Bảng playlists:
-- Thêm chỉ mục cho `user_id` để tăng tốc truy vấn danh sách phát theo người dùng.
CREATE INDEX idx_playlists_user ON playlists (user_id);
-- Thêm chỉ mục cho `title` để tìm kiếm danh sách phát theo tên.
CREATE INDEX idx_playlists_title ON playlists (title);

-- Bảng songs:
-- Thêm chỉ mục cho `album_id` để tăng tốc truy vấn bài hát theo album.
CREATE INDEX idx_songs_album ON songs (album_id);
-- Thêm chỉ mục cho `artist_id` để tăng tốc truy vấn bài hát theo nghệ sĩ.
CREATE INDEX idx_songs_artist ON songs (artist_id);
-- Thêm chỉ mục kết hợp `is_pending` và `is_deleted` để lọc bài hát trạng thái.
CREATE INDEX idx_songs_status ON songs (is_pending, is_deleted);
-- Thêm chỉ mục cho `title` để tìm kiếm bài hát theo tên.
CREATE INDEX idx_songs_title ON songs (title);

-- Bảng genre_song:
-- Thêm chỉ mục kết hợp `genre_id` và `song_id` để tránh trùng lặp và tối ưu hóa truy vấn.
CREATE UNIQUE INDEX idx_genre_song ON genre_song (genre_id, song_id);

-- Bảng favourite_song:
-- Thêm chỉ mục kết hợp `user_id` và `song_id` để tối ưu hóa truy vấn yêu thích bài hát.
CREATE UNIQUE INDEX idx_favourite_songs ON favourite_songs (user_id, song_id);

-- Bảng playlist_song:
-- Thêm chỉ mục kết hợp `playlist_id` và `song_id` để tối ưu hóa truy vấn bài hát trong danh sách phát.
CREATE UNIQUE INDEX idx_playlist_song ON playlist_song (playlist_id, song_id);

