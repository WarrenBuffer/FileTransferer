import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Response } from '../models/server-response';
import { MessageService } from 'primeng/api';
import { AuthService } from './auth.service';
import { catchError, map, Observable, of } from 'rxjs';
import { UserFile } from '../models/user-file';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private basePath = "http://10.1.11.6:8080/api";

  constructor(private _http: HttpClient, private messageService: MessageService, private authService: AuthService) { }

  getFiles(): Observable<any> {
    return this._http.get<Response>(`${this.basePath}/userFiles`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('Authorization') ?? ''
      }
    }).pipe(
      map(v => {
        if (v.code === -1) {
          this.showError(v.message);
          this.authService.logout();
        } else if (v.code !== 0) {
          this.showError(v.message);
        } else {
          return v.message;
        }
      }),
      catchError(err => {
        if (err.status === 403) {
          this.authService.logout();
        }
        return of(undefined);
      })
    );
  }

  uploadFiles(file: any) {
    const fd = new FormData();
    fd.append('file', file);
    this._http.post<Response>(`${this.basePath}/upload`, fd, {
      headers: {
        'Authorization': sessionStorage.getItem('Authorization') ?? ''
      }
    }).subscribe({
      next: v => {
        if (v.code === -1) {
          this.showError(v.message);
          this.authService.logout();
        } else if (v.code !== 0) {
          this.showError(v.message);
        } else {
          console.log("Successfully uploaded")
          this.showSuccess(v.message);
        }
      },
      error: err => {
        if (err.status === 403) this.authService.logout();
        this.showError(err.message)
      }
    })
  }

  deleteFile(id: number): Observable<any> {
    return this._http.get<Response>(`${this.basePath}/delete/${id}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('Authorization') ?? ''
      }
    }).pipe(
      map(v => {
        if (v.code === -1) {
          this.showError(v.message);
          this.authService.logout();
        } else if (v.code !== 0) {
          this.showError(v.message);
        } else {
          this.showSuccess("File deleted successfully.");
          return v.message;
        }
      }),
      catchError(err => {
        this.showError(err.message);
        return of(undefined);
      })
    );
  }
  
  downloadFile(id: number): Observable<any> {
    return this._http.get(`${this.basePath}/download/${id}`, {
      responseType: 'arraybuffer',
      headers: {
        'Authorization': sessionStorage.getItem('Authorization') ?? ''
      },
    }).pipe(
      map(v => {
        return v;
      }),
      catchError(err => {
        console.log(err);
        if (err.status === 403) this.authService.logout();
        this.showError(err.message);
        return of(undefined);
      })
    );
  }

  showError(message: string) {
    this.messageService.add({ key: 'br', severity: 'error', summary: 'Error', detail: message });
  }

  showSuccess(message: string) {
    this.messageService.add({ key: 'br', severity: 'success', summary: 'Success', detail: message });
  }
}
