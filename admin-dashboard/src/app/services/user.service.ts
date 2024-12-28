import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  fetchUsers(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/users`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  getUserCount(): Observable<number> {
    return this.fetchUsers().pipe(
      map((response) => {
        if (!Array.isArray(response)) {
          console.error('Response không phải là một mảng:', response);
          return 0;
        }
        return response.length;
      })
    );
  }

  createUser(userData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/register`, userData).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }

  detailUser(userId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/public/users/${userId}`).pipe(
      catchError((err) => {
        return throwError(() => ({
          status: err.status,
          errors: err.error?.listError || [],
        }));
      })
    );
  }
}
