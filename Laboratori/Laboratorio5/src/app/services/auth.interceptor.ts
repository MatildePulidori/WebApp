import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthService} from "../auth/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private auth: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler):
    Observable<HttpEvent<unknown>> {
    const accessToken = this.auth.getToken();
    if (accessToken) {
      const cloned = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + accessToken)
      });
      console.log('AuthInterceptor accessToken found: ' + JSON.stringify(accessToken));
      return next.handle(cloned);
    } else {
      console.log('AuthInterceptor accessToken not found');
      return next.handle(request);
    }
  }


}
