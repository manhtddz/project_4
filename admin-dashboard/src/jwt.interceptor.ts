// import { Injectable } from '@angular/core';
// import {
//   HttpRequest,
//   HttpHandler,
//   HttpEvent,
//   HttpInterceptor,
//   HttpErrorResponse,
// } from '@angular/common/http';
// import { Router } from '@angular/router';
// import { catchError, Observable, throwError } from 'rxjs';
// import { ToastrService } from 'ngx-toastr';

// @Injectable({
//   providedIn: 'root',
// })
// export class JwtInterceptor implements HttpInterceptor {
//   constructor(private router: Router, private toastr: ToastrService) {}

//   // intercept(
//   //   request: HttpRequest<any>,
//   //   next: HttpHandler
//   // ): Observable<HttpEvent<any>> {
//   //   const token = localStorage.getItem('authToken');

//   //   // Nếu có token, thêm Authorization header vào request
//   //   if (token) {
//   //     request = request.clone({
//   //       setHeaders: {
//   //         Authorization: `Bearer ${token}`,
//   //       },
//   //     });
//   //   }

//   //   return next.handle(request).pipe(
//   //     catchError((error: HttpErrorResponse) => {
//   //       if (error.status === 401) {
//   //         this.router.navigate(['/login']);
//   //         this.toastr.error(
//   //           'Token is invalid or expired. Please log in again.',
//   //           'Unauthorized',
//   //           {
//   //             timeOut: 3000,
//   //           }
//   //         );

//   //         return new Observable<HttpEvent<any>>();
//   //       }

//   //       this.toastr.error(
//   //         error.message || 'An unknown error occurred',
//   //         'Error',
//   //         {
//   //           timeOut: 3000,
//   //         }
//   //       );
//   //       return new Observable<HttpEvent<any>>();
//   //     })
//   //   );
//   // }

//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     const token = localStorage.getItem('token'); // Lấy token từ localStorage

//     // Nếu có token, thêm vào header Authorization
//     if (token) {
//       req = req.clone({
//         setHeaders: {
//           Authorization: `Bearer ${token}`,
//         },
//       });
//     }

//     return next.handle(req);
//   }
// }
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class JwtInterceptor implements HttpInterceptor {
  constructor(private router: Router, private toastr: ToastrService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('authToken'); 

    // Nếu có token, thêm vào header Authorization
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    // Gửi yêu cầu HTTP và xử lý lỗi nếu có
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.toastr.error(
            'Token is invalid or expired. Please log in again.',
            'Unauthorized',
            {
              timeOut: 3000,
            }
          );
          
          // Token hết hạn hoặc không hợp lệ, điều hướng về trang đăng nhập
          this.router.navigate(['/login']);
          this.toastr.error(
            'Token is invalid or expired. Please log in again.',
            'Unauthorized',
            {
              timeOut: 3000,
            }
          );
          return throwError(() => new Error('Unauthorized'));
        }

        // Xử lý lỗi khác
        this.toastr.error(
          error.message || 'An unknown error occurred',
          'Error',
          {
            timeOut: 3000,
          }
        );
        return throwError(() => error); // Trả lại lỗi để xử lý tiếp
      })
    );
  }
}
