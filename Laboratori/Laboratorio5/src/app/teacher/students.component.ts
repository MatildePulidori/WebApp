import {Component,  Input, Output,  OnInit, ViewChild, EventEmitter} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Student} from "../student.model";
import {MatTableDataSource} from "@angular/material/table";
import {Observable, of} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {map, startWith} from "rxjs/operators";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})

export class StudentsComponent implements OnInit {

  title = 'Laboratorio5';

  @Input('enrolledStudents')
  students: Student[];

  @Input()
  set enrolledStudents(students: Student[]){
    this.students=students;
    this.dataSource.data=students;
  }

  @Input()
  available_students: Student[];

  @Input()
  set notEnrolledStudents(availableStudents: Student[]){
    this.available_students=availableStudents;
    this.filteredAvailableStudents = of(this.available_students);
  }

  @Output()
  added_students= new EventEmitter<Student[]>();

  @Output()
  students_deleted= new EventEmitter<Student[]>();

  filteredAvailableStudents: Observable<Student[]>;
  dataSource;
  myControl = new FormControl();
  displayedColums: string[] = ['select', 'serial', 'firstName', 'name'];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  // ---------- Inizializzazione

  constructor() {
    this.dataSource =new MatTableDataSource<Student>();
  }

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

  // ---------- Selezione degli studenti

  allComplete: boolean;
  someComplete: boolean;

  onSelection(event: MatCheckboxChange, student: Student) {
    student.checked = student.checked !== true;
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

  // ---------- Eliminazione gli studenti selezionati
  deleteStudents() {
    this.students_deleted.emit();

    this.filteredAvailableStudents = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => value ? this._filter(value): this.available_students.slice() )
    )
    this.updateMasterCheckbox();
  }

  // ---------- Aggiunta di uno degli studenti disponibili

  studentToAdd: Student;
  studentSelected(event: MatAutocompleteSelectedEvent) {
    this.studentToAdd = event.option.value;
  }

  addStudent(){
    if (this.studentToAdd === undefined) {
      return;
    }
    this.added_students.emit([this.studentToAdd]);
    // this.dataSource.data = this.students;
    this.studentToAdd = null;
    this.myControl.setValue("");
  }

  // ---------- Sort degli studenti

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
        case 'serial': return this.compare(a.id, b.id, isAsc);
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'firstName': return this.compare(a.firstName, b.firstName, isAsc);
        default: return 0;
      }
    });
    this.dataSource.data = this.sortedStudents;
    this.students = this.sortedStudents;
  }

  private compare( a: string | number, b: string | number, isAsc: boolean){
    return(a<b? -1 : 1) * (isAsc? 1: -1 );
  }

}
