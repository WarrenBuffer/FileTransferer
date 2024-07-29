import { Component } from '@angular/core';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { DashboardService } from '../services/dashboard.service';

@Component({
    selector: 'app-upload',
    templateUrl: './upload.component.html',
    styleUrl: './upload.component.css'
})
export class UploadComponent {
    files = [];

    totalSize: number = 0;

    totalSizePercent: number = 0;

    constructor(private config: PrimeNGConfig, private messageService: MessageService, private dashService: DashboardService) { }

    choose(event: any, callback: any) {
        callback();
    }

    onRemoveTemplatingFile(event: any, file: any, removeFileCallback: any, index: any) {
        removeFileCallback(event, index);
        this.totalSize -= parseInt(this.formatSize(file.size));
        this.totalSizePercent = this.totalSize / 10;
    }

    onClearTemplatingUpload(clear: any) {
        clear();
        this.totalSize = 0;
        this.totalSizePercent = 0;
    }

    onSelectedFiles(event: any) {
        this.files = event.currentFiles;
        this.files.forEach((file: any) => {
            this.totalSize += parseInt(this.formatSize(file.size));
        });
        this.totalSizePercent = this.totalSize / 10;
    }

    uploadEvent(callback: any) {
        this.showLoading();
        this.files.forEach(file => this.dashService.uploadFiles(file));
        callback();
        this.totalSize = 0;
        this.totalSizePercent = 0;
        this.files = [];
    }

    formatSize(bytes: any) {
        const k = 1024;
        const dm = 3;
        const sizes = this.config.translation.fileSizeTypes;
        if (bytes === 0 && sizes !== undefined) {
            return `0 ${sizes[0]}`;
        }

        const i = Math.floor(Math.log(bytes) / Math.log(k));
        const formattedSize = parseFloat((bytes / Math.pow(k, i)).toFixed(dm));
        if (sizes !== undefined)
            return `${formattedSize} ${sizes[i]}`;
        return `${formattedSize} ${0}`
    }

    showLoading() {
        this.messageService.add({ key: 'br', severity: 'info', summary: 'Info', detail: "Uploading file. Please wait." });
    }
}
