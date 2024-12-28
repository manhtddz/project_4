import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { UserService } from '../../services/user.service';
import { SongService } from '../../services/song.service';
import { PlaylistService } from '../../services/playlist.service';
import { AlbumService } from '../../services/album.service';
import { ArtistService } from '../../services/artist.service';
import { GenreService } from '../../services/genre.service';
import { KeywordService } from '../../services/keyword.service';
import { NewsService } from '../../services/news.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})
export class SidebarComponent implements OnInit {
  userCount: number = 0;
  songCount: number = 0;
  playlistCount: number = 0;
  albumCount: number = 0;
  artistCount: number = 0;
  genreCount: number = 0;
  categoryCount: number = 0;
  keywordCount: number = 0;
  newsCount: number = 0;
  constructor(
    private router: Router,
    private songService: SongService,
    private playlistService: PlaylistService,
    private categoryService: CategoryService,
    private albumService: AlbumService,
    private artistService: ArtistService,
    private genreService: GenreService,
    private newsService: NewsService,
    private keywordService: KeywordService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.countUsers();
    this.countSongs();
    this.countPlaylists();
    this.countAlbums();
    this.countArtists();
    this.countGenres();
    this.countCategories();
    this.countKeywords();
    this.countNews();
  }

  isActive(urls: string[]): boolean {
    return urls.some((url) => this.router.url.startsWith(url));
  }

  onLogout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  countUsers(): void {
    this.userService.getUserCount().subscribe({
      next: (count) => {
        this.userCount = count;
      },
      error: (err) => {
        console.error('Error fetching user count:', err);
      },
    });
  }

  countSongs(): void {
    this.songService.getSongCount().subscribe({
      next: (count) => {
        this.songCount = count;
      },
      error: (err) => {
        console.error('Error fetching song count:', err);
      },
    });
  }

  countPlaylists(): void {
    this.playlistService.getPlaylistCount().subscribe({
      next: (count) => {
        this.playlistCount = count;
      },
      error: (err) => {
        console.error('Error fetching playlist count:', err);
      },
    });
  }

  countAlbums(): void {
    this.albumService.getAlbumCount().subscribe({
      next: (count) => {
        this.albumCount = count;
      },
      error: (err) => {
        console.error('Error fetching album count:', err);
      },
    });
  }

  countArtists(): void {
    this.artistService.getArtistCount().subscribe({
      next: (count) => {
        this.artistCount = count;
      },
      error: (err) => {
        console.error('Error fetching user count:', err);
      },
    });
  }

  countGenres(): void {
    this.genreService.getGenreCount().subscribe({
      next: (count) => {
        this.genreCount = count;
      },
      error: (err) => {
        console.error('Error fetching genre count:', err);
      },
    });
  }

  countKeywords(): void {
    this.keywordService.getKeywordCount().subscribe({
      next: (count) => {
        this.keywordCount = count;
      },
      error: (err) => {
        console.error('Error fetching keyword count:', err);
      },
    });
  }

  countNews(): void {
    this.newsService.getNewsCount().subscribe({
      next: (count) => {
        this.newsCount = count;
      },
      error: (err) => {
        console.error('Error fetching news count:', err);
      },
    });
  }

  countCategories(): void {
    this.categoryService.getCategoryCount().subscribe({
      next: (count) => {
        this.categoryCount = count;
      },
      error: (err) => {
        console.error('Error fetching subject count:', err);
      },
    });
  }
}
