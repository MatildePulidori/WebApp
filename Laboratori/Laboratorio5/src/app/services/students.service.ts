import { Injectable } from '@angular/core';
import {Student} from "../student.model";
import {forkJoin, from, Observable, of} from "rxjs";
import {concatMap, filter, map, mergeMap, toArray} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class StudentsService {

  constructor(private  http: HttpClient) {}


  allStudentsURL = "/students";
  studentsEnrolledURL = "/students?courseId=1";
  studentsAvailableURL = "/students?courseId=0";


  getStudents(): Observable<Student[]>{
    return this.http.get<Student[]>(this.allStudentsURL);
  }

  getStudentsEnrolled(): Observable<Student[]>{
    return this.http.get<Student[]>(this.studentsEnrolledURL) ;
  }

  getAvailableStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.studentsAvailableURL);
  }

  createStudent(s: Student): void{
    this.http.post(this.allStudentsURL, s).subscribe( () => console.log('created student '+ s.name));;
  }

  updateStudent(s: Student): void{
    this.http.put(this.allStudentsURL+"/"+s.id, s).subscribe( () => console.log('updated student '+ s.name));
  }

  deleteStudent(s: Student):void{
    this.http.delete(this.allStudentsURL+"/"+s.id). subscribe( () => console.log('deleted student '+ s.name));;
  }

  updateEnrolled(studentsToUpdate: Student[], enroll:  boolean): Observable<Student[]> {
      studentsToUpdate.forEach(student => {
        student.courseId = enroll? 1: 0;
        delete student.checked;
      });

     // SERIALE

     /*return from(studentsToUpdate).pipe(
        concatMap( s => this.http.put(this.allStudentsURL+"/"+s.id, s)),
        toArray(),
        concatMap( () => this.http.get<Student[]>(this.studentsEnrolledURL)),
       );
      */


    // PARALLELO
   return forkJoin(from(studentsToUpdate).pipe(
      mergeMap( s => this.http.put(this.allStudentsURL+"/"+s.id, s)),
      toArray()
    )).pipe(
      concatMap( () => this.http.get<Student[]>(this.studentsEnrolledURL))
    );
  }



}
