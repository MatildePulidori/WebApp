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
    if(request.url !== 'http://localhost:300/login' && request.url !== 'http://localhost:300/signin'
      && request.url !== 'http://localhost:300/register' && request.url !== 'http://localhost:300/signup') {
      if (accessToken) {
        const cloned = request.clone({
          headers: request.headers.set('Authorization', 'Bearer ' + accessToken)
        });
        return next.handle(cloned);
      } else {
        return next.handle(request);
      }
    } else{
      console.log('interceptor found request directed to ' + request.url + ' so it returns the request as-is');
      return next.handle(request);
    }
  }


}
