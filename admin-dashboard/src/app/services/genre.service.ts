import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchGenres(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/genres`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getGenreCount(): Observable<number> {
    return this.fetchGenres().pipe(
      map((response) => {
        return Array.isArray(response) ? response.length : 0;
      })
    );
  }
}
