import { Injectable } from '@angular/core';
import { User } from '../model/User';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

let API_URL = 'http://localhost:8765/api/user/service/';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  logIn(user: User): Observable<any> {
    const headers = new HttpHeaders(user ? {
      authorization: 'Basic ' + btoa(user.username + ':' + user.password)
    } : {});

    return this.http.get<any>(API_URL + 'user', {headers})
      .pipe(map(res => {
        if (res) {
            localStorage.setItem('currentUser', JSON.stringify(res));
        }
        return res;
      }));
  }

  register(user: User): Observable<any> {
    return this.http.post(
      API_URL + 'registration',
      JSON.stringify(user),
      {
        headers: {
          'Content-Type':'application/json; charset=UTF-8'
        }
      });
  }

  logOut(): Observable<any> {
    return this.http.post(API_URL + 'logout', {})
      .pipe(map(res => {
        localStorage.removeItem('currentUser');
      }));
  }
}
