import { Component } from '@angular/core';
import { User } from '../models/user';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username!: string;
  password!: string;
  constructor(private authService: AuthService, private router: Router) { 
  }

  login() {
    let user = new User();
    user.username = this.username;
    user.password = this.password;
    this.authService.login(user);
  }
}
