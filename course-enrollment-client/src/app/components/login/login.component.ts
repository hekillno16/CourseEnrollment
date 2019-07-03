import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/User';
import { AuthService } from 'src/app/services/auth.service';
import { EmitterService } from 'src/app/services/emitter.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(
    private authService: AuthService,
    private router: Router,
    private emitterService: EmitterService
  ) { }

  ngOnInit() {
  }

  login() {
    this.authService.logIn(this.user)
      .subscribe(
        data => {
          this.emitterService.emitLogin(JSON.stringify(data));
          this.router.navigate(['/profile']);
        },
        err => {
          this.errorMessage = 'username or password is incorrect';
        }
      )
  }
}
