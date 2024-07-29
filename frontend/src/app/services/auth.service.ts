import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { MessageService } from 'primeng/api';
import { User } from '../models/user';
import { Response } from '../models/server-response';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private basePath = "http://10.1.11.6:8080";
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private httpOptions = {
    headers: this.headers,
  }
  
  constructor(private _http: HttpClient, private cookieService: CookieService, private messageService: MessageService, private router: Router) {
  }

  isLoggedIn() {
    return sessionStorage?.getItem('Authorization') !== null;
  }

  register(user: User) {
    this._http.post<Response>(`${this.basePath}/register`, user,
      this.httpOptions).subscribe({
        next: v => {
          if (v.code !== 0) {
            this.showError(v.message);
          } else {
            this.showSuccess(v.message);
          }
        },
        error: err => {
          this.showError(err.message);
        }
      });
  }

  login(user: User) {
    this._http.post<Response>(`${this.basePath}/login`, user,
      this.httpOptions).subscribe({
        next: v => {
          if (v.code !== 0) {
            this.showError(v.message);
          } else {
            sessionStorage.setItem('Authorization', v.message);
            this.router.navigate(['dashboard'])
          }
        },
        error: err => {
          this.showError(err.message);
        }
      });
  }

  logout(): void {
    this._http.get<Response>(`${this.basePath}/logout`, this.httpOptions).subscribe({
      next: v => {
        sessionStorage.removeItem('Authorization');
        this.showSuccess(v.message)
        this.router.navigate(['login']);
      }
    });
  }

  showError(message: string) {
    this.messageService.add({key: 'br', severity:'error', summary: 'Error', detail: message});
  }

  showSuccess(message: string) {
    this.messageService.add({key: 'br', severity:'success', summary: 'Success', detail: message});
  }
}
