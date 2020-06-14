import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule, HttpResponse} from "@angular/common/http";
import {catchError, map, tap} from "rxjs/operators";
import {Observable} from "rxjs";
declare var require: any
let moment = require("moment");

interface Token {
  accessToken: string;

}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
  currentToken = '';
  tokenExpirationTime;
  success = true;
  redirectUrl = '';

  login(email: string, password: string): Observable<boolean> {

    return this.http.post("/login", {email, password})
      .pipe(
        tap((data: Token) => {
          this.currentToken = data.accessToken;
          this.tokenExpirationTime = moment().add(1, 'hours').calendar();
          },
            err => { this.setError();}
        ),
        map( (data: Token) => true  ));
  }

  logout(){
    this.currentToken='';
  }

  getToken(): string{
    return this.currentToken;
  }

  setError(){
    this.success = false;
  }

  isTokenExpired():boolean{
    const isExpired = moment().calendar() >= this.tokenExpirationTime;
    if( isExpired) this.currentToken='';
    return isExpired;
  }
}
