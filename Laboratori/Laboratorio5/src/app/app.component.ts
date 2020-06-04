import {Component, OnDestroy, OnInit, ViewChild} from "@angular/core";
import {MatSidenav} from "@angular/material/sidenav";
import {MatDialog} from "@angular/material/dialog";
import {LoginDialogComponent} from "./auth/login-dialog.component";
import {AuthService} from "./auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

interface QueryParam {
  doLogin: boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit, OnDestroy{
  title: 'Laboratorio5';
  logged: boolean;
  userEmail: string;
  paramMapSubscription;
  constructor(public dialog : MatDialog, public router: Router ,private route: ActivatedRoute, private auth: AuthService) {
  }

  @ViewChild(MatSidenav) matSideNav: MatSidenav;
  toggleForMenuClick(){
    this.matSideNav.toggle();
  };


  openDialog(): void {
    const dialogRef= this.dialog.open(LoginDialogComponent, { data: {logged: this.logged}});

    dialogRef.afterClosed().subscribe(result => {
      if ( result ) {
        if ( !result.authResult  ) { // logout
          const URL = this.router.url;
          this.router.navigate(['/home']);
        }
        // success
        this.logged = result.authResult;
        this.userEmail = (result.authResult ? result.email : '');
        if ( this.auth.redirectUrl ) {
          const URL = this.auth.redirectUrl;
          this.auth.redirectUrl = '';
          this.router.navigate([URL]);
        }
      }
      else { // premuto bottone cancel nella finestra modale
        this.router.navigate(['/home']);
      }
    });
  }

  getCurrentCourseFromURL(): string {
    if (this.router.url.includes('/home') || this.router.url === '/') {
      return '';
    }
    return this.router.url.split('/')[3].replace(/-/g, ' ').split(' ').map(s => s.charAt(0).toUpperCase() + s.slice(1)).join(' ');
  }

  ngOnInit(): void {
    this.paramMapSubscription = this.route.queryParams.subscribe( (params: QueryParam) => {
      if ( params.doLogin ) {
        this.openDialog();
      }
    } );
  }

  ngOnDestroy(): void {
    this.paramMapSubscription.unsubscribe();
  }
}




