import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {AuthService} from "./auth.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {timer} from "rxjs";

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent implements OnInit {

  loginSuccess = false;
  wrongCredentials = false;
  constructor(private auth: AuthService, public  fb: FormBuilder, public dialogRef: MatDialogRef<LoginDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  public loginForm = this.fb.group( {
    email: [ '', [Validators.required, Validators.email] ],
    password: ['', [Validators.required]]
  });

  ngOnInit(): void {
    if(this.data.logged){
      this.auth.logout();
      const timeout = timer(1000);
      timeout.subscribe( () => {
        this.dialogRef.close({authResult: false, email: ''});
      } );
    }
  }

  getErrorMessageEmail() {
    if (this.loginForm.get('email').hasError('required')) {
      return 'Inserire la mail';
    }
    return this.loginForm.get('email').hasError('email') ? 'Email non valida' : '';
  }

  getErrorMessagePassword() {
    if (this.loginForm.get('password').hasError('required')) {
      return 'Inserire la password';
    }
  }

  loginUser() {
    this.auth.login(this.loginForm.get('email').value, this.loginForm.get('password').value)
      .subscribe( succes => {
        this.loginSuccess = true;
        const timeout = timer(2000);
        timeout.subscribe( () => {
          this.loginSuccess = false;
          this.dialogRef.close({authResult: true, email: this.loginForm.get('email').value});
        } );
      }, error => {
        this.loginSuccess = false;
        this.wrongCredentials = true;
      } );
  }
}
