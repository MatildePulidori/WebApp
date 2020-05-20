import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {Student} from "./student.model";
import { MatCheckboxChange} from "@angular/material/checkbox";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatSort, Sort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{
  title = 'Laboratorio4';
  myControl = new FormControl();
  displayedColums: string[] = ['select', 'id', 'name', 'firstName'];
  students:  Student[] = [
    new Student( '265353', 'Brad', 'Pitt'),
    new Student( '262705', 'Jennifer', 'Aniston')];
  dataSource=new MatTableDataSource<Student>(this.students);
  available_students:  Student[] = [
    new Student( '269988', 'Matthew', 'McConaughey'),
    new Student( '265588', 'Emma', 'Stone'),
    new Student( '234567', 'Emma', 'Watson')];
  filteredAvailableStudents: Observable<Student[]>;
  @ViewChild(MatSidenav) matSideNav: MatSidenav;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit(){
    this.filteredAvailableStudents = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => value ? this._filter(value): this.available_students.slice() )
    )
    this.dataSource.paginator = this.paginator;
  }
  private _filter(value:string | Student): Student[]{
    let filterValue;
    if (!(value instanceof Student)) {
      filterValue = value.toLowerCase();
    } else filterValue = value.name.toLowerCase();
    return this.available_students.filter(option => option.name.toLowerCase().indexOf(filterValue)===0)
  }

  displayFunc(student: Student): string{
    return student && student.toString();
  }

  allComplete: boolean;
  someComplete: boolean;

  toggleForMenuClick(){
    this.matSideNav.toggle();
  };

  onSelection(event: MatCheckboxChange, student:Student) {
    student.toggleSelect();
    this.updateMasterCheckbox();
  }

  updateMasterCheckbox(){
    let completed = this.computeChecked();
    this.allComplete = ( completed == this.students.length && completed !== 0) ;
    this.someComplete = ( completed>0 &&  completed<this.students.length);
  }
  computeChecked(){
    let checkedCount = 0;
    for(let i = 0; i < this.students.length; i++) {
      if (this.students[i].checked) {
        checkedCount++;
      }
    }
    return checkedCount;
  }

  setAll(checked: boolean) {
    for(let i = 0; i < document.getElementsByTagName('mat-checkbox').length -1; i++){
      this.students[i].checked = checked;
    }
  }

  deleteStudents() {
    let new_students: Student[] = [];
    let new_available_students: Student[]=  [];
    for (let i = 0; i < this.available_students.length; i++) {
      this.available_students[i].checked=false;
      new_available_students.push(this.available_students[i]);
    }
    for (let i = 0; i < this.students.length; i++) {

      if (this.students[i].checked == false) {
        this.students[i].checked=false;
        new_students.push(this.students[i]);
      } else {
        this.students[i].checked=false;
        new_available_students.push(this.students[i]);
      }


    }
    this.students = new_students;
    this.dataSource.data = new_students;
    this.available_students = new_available_students;
    this.filteredAvailableStudents = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => value ? this._filter(value): this.available_students.slice() )
    )
    this.updateMasterCheckbox();
  }

  studentToAdd: Student;
  studentSelected(event: MatAutocompleteSelectedEvent) {
    this.studentToAdd = event.option.value;
  }

  addStudent() {
    if (this.studentToAdd === undefined) {
      return;
    }
    let new_students: Student[] = [];
    for (let i = 0; i < this.students.length; i++) {
      new_students.push(this.students[i]);
    }
    new_students.push(this.studentToAdd);
    this.students = new_students;
    this.dataSource.data = new_students;
    let indexToRemove = this.available_students.findIndex(s => s.id === this.studentToAdd.id);
    this.available_students.splice(indexToRemove, 1);
    this.studentToAdd = null;
    this.myControl.setValue("");
  }

  sortedStudents: Student[] = this.students;

  sortData(sort: Sort) {
    const data = this.students.slice();
    if(!sort.active || sort.direction==""){
      this.sortedStudents = data;
      return;
    }

    this.sortedStudents = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id': return this.compare(a.id, b.id, isAsc);
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'firstName': return this.compare(a.firstName, b.firstName, isAsc);
        default: return 0;
      }
    });
    this.dataSource.data = this.sortedStudents;
    this.students = this.sortedStudents;
  }

  private compare( a: string, b: string, isAsc: boolean){
    return(a<b? -1 : 1) * (isAsc? 1: -1 );
  }



}




