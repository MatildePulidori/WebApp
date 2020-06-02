import {Component, ViewChild} from "@angular/core";
import {MatSidenav} from "@angular/material/sidenav";
import {MatDialog} from "@angular/material/dialog";
import {LoginDialogComponent} from "./auth/login-dialog.component";
import {AuthService} from "./auth/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent{
  title: 'Laboratorio5';
  logged: boolean;
  constructor(private dialog : MatDialog) {
  }

  @ViewChild(MatSidenav) matSideNav: MatSidenav;
  toggleForMenuClick(){
    this.matSideNav.toggle();
  };


  openDialog(): void {
    const dialogRef= this.dialog.open(LoginDialogComponent, { data: {logged: this.logged}});

    dialogRef.afterClosed().subscribe(result => {
      this.logged = result;
    });
  }
}




