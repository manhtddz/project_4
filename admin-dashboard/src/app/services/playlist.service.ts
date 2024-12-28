import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PlaylistService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchPlaylists(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/playlists/display`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getPlaylistCount(): Observable<number> {
    return this.fetchPlaylists().pipe(
      map((response) => {
        return Array.isArray(response) ? response.length : 0;
      })
    );
  }
}
