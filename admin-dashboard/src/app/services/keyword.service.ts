import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KeywordService {
private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchKeywords(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/keywords`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getKeywordCount(): Observable<number> {
    return this.fetchKeywords().pipe(
      map((response) => {
        return Array.isArray(response) ? response.length : 0;
      })
    );
  }
}
