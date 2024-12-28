import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchAlbums(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/albums/display`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getAlbumCount(): Observable<number> {
    return this.fetchAlbums().pipe(
      map((response) => {
        return Array.isArray(response) ? response.length : 0;
      })
    );
  }
}
