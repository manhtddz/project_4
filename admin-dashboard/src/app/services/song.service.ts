import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SongService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchSongs(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/songs/display`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getSongCount(): Observable<number> {
    return this.fetchSongs().pipe(
      map((response) => {
        return Array.isArray(response) ? response.length : 0;
      })
    );
  }
}
