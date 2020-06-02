import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {AuthService} from "./auth.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent implements OnInit {

  constructor(private auth: AuthService, public  fb: FormBuilder, public dialogRef: MatDialogRef<LoginDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  public loginForm = this.fb.group( {
    email: [ '', [Validators.required, Validators.email] ],
    password: ['', [Validators.required]]
  });

  ngOnInit(): void {
    if(this.data.logged){
      this.auth.logout();
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
        this.dialogRef.close(true);
        }, error => {
        this.dialogRef.close(false);
      });
  }
}
