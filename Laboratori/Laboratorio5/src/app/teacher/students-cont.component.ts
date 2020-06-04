import { Component, OnInit } from '@angular/core';
import {Student} from "../student.model";
import {StudentsService} from "../services/students.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-students-cont',
  templateUrl: './students-cont.component.html',
  styleUrls: ['./students-cont.component.css']
})
export class StudentsContComponent implements OnInit {

  students:  Student[] = [];

  available_students:  Student[] = [];

  constructor(private studentService:  StudentsService, private router: Router) {
  }

  ngOnInit(): void {
    this.studentService.getStudentsEnrolled()
      .subscribe((data)=> {
        data.map( o => Object.setPrototypeOf(o, Student.prototype));
        this.students = data;
      });
    this.studentService.getAvailableStudents()
      .subscribe(
        (data) => {
          data.map( o => Object.setPrototypeOf(o, Student.prototype));
          this.available_students = data;
        });
  }

  addStudent(data: Student[]) {
    if(data[0]) {
      let indexToRemove = this.available_students.findIndex(s => (s.id === data[0].id));
      this.available_students.splice(indexToRemove, 1);
    }
    this.studentService.updateEnrolled([data[0]], true)
      .subscribe( students => this.students = students.map( o => Object.setPrototypeOf(o, Student.prototype)).slice() );

  }

  deleteStudents() {
    let removed_students: Student[] = [];
    for (let i=0; i<this.students.length; i++){
      if (this.students[i].checked === true) {
        this.available_students.push(this.students[i]);
        removed_students.push(this.students[i]);
      }
      this.students[i].checked = false;
    }
    this.studentService.updateEnrolled(removed_students, false)
      .subscribe( students => this.students = students.map( o => Object.setPrototypeOf(o, Student.prototype)).slice());

  }


}
