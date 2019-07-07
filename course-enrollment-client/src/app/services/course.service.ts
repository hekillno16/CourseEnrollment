import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../model/Transaction';
import { Observable } from 'rxjs';

let API_URL = 'http://localhost:8765/api/course/service/';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(
    private http: HttpClient
  ) { }

  enroll(transaction: Transaction): Observable<any> {
    return this.http.post(
      API_URL + 'enroll',
      JSON.stringify(transaction),
      {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      }
    );
  }

  findAllCourses(): Observable<any> {
    return this.http.post(
      API_URL,
      {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      }
    );
  }

  filterStudents(courseId: string): Observable<any> {
    return this.http.post(
      API_URL + 'students',
      courseId,
      {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      }
    );
  }

  popularCourses(): Observable<any> {
    return this.http.get(
      API_URL + 'popular',
      {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      }
    )
  }

  filterCourses(text: string): Observable<any> {
    return this.http.post(
      API_URL + 'filter',
      text,
      {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      }
    );
  }

}
