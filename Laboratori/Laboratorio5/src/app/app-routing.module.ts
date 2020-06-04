import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./teacher/home.component";
import {PageNotFoundComponent} from "./teacher/page-not-found.component";
import {StudentsContComponent} from "./teacher/students-cont.component";
import {VmsContComponent} from "./teacher/vms-cont.component";
import {AuthGuard} from "./auth/auth.guard";


const routes: Routes = [
  {path: "home", component: HomeComponent},
  {
    path: "teacher/courses",
    canActivate: [AuthGuard],
    children: [
      {
        path:  "applicazioni-internet/students",
        canActivate: [AuthGuard],
        component: StudentsContComponent
      },
      {
        path:  "applicazioni-internet/vmss",
        canActivate: [AuthGuard],
        component: VmsContComponent
      }
    ]
 },
  {path: "**", component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing:false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
