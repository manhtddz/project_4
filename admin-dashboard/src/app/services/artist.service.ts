import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {
private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchArtists(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/artists`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getArtistCount(): Observable<number> {
    return this.fetchArtists().pipe(
      map((response) => {
        return Array.isArray(response) ? response.length : 0;
      })
    );
  }
}
