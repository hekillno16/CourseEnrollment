import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Log } from '../model/Log';
import { Observable } from 'rxjs';

let API_URL = 'http://localhost:8765/api/log/service/';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(
    private http: HttpClient
  ) { }

  createLog(log: Log): Observable<any> {
    return this.http.post(
      API_URL + 'create',
      JSON.stringify(log),
      {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      }
    );
  }

  getSummaryOfCourse(courseId: string): Observable<any> {
    return this.http.post(
      API_URL + 'summary',
      courseId,
      {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      }
    );
  }

  getIpClient(): Observable<any> {
    return this.http.get('http://api.ipify.org/?format=json')
  }
}
