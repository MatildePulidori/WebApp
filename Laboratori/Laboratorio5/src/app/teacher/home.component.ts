import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-home',
  template: `<h2>Home</h2>`
})
export class HomeComponent implements OnInit {
    path: string;
    constructor(private route: ActivatedRoute) {}
    ngOnInit() {
    }
}
