import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchCategories(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/categories`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getCategoryCount(): Observable<number> {
    return this.fetchCategories().pipe(
      map((response) => {
        if (!Array.isArray(response)) {
          console.error('Response không phải là một mảng:', response);
          return 0;
        }
        return response.length;
      })
    );
  }

}
