import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./teacher/home.component";
import {PageNotFoundComponent} from "./teacher/page-not-found.component";
import {StudentsContComponent} from "./teacher/students-cont.component";
import {VmsContComponent} from "./teacher/vms-cont.component";


const routes: Routes = [
  {path: "home", component: HomeComponent},
  {path:"teacher/course/applicazioni-internet/students", component: StudentsContComponent},
  {path:"teacher/course/applicazioni-internet/vms", component: VmsContComponent},
  {path: "**", component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing:false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
