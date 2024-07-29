import { Component } from '@angular/core';
import { DashboardService } from '../services/dashboard.service';
import { error } from 'console';
import { UserFile } from '../models/user-file';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  files!: UserFile[];

  constructor(private dashService: DashboardService) {
    this.getFiles();
  }

  getFiles() {
    this.dashService.getFiles().subscribe({
      next: v => {
        this.files = v;
      }
    })
  }

  delete(id: number) {
    this.dashService.deleteFile(id).subscribe({
      next: v => {
        this.files = v;
      }
    });
  }

  download(id: number, filename: string) {
    this.dashService.downloadFile(id).subscribe({
      next: response => {
        const blob = new Blob([response]);
        console.log(blob)
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      }
    });
  }
}
