;
import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ToolbarModule } from 'primeng/toolbar';
import { SplitButtonModule } from 'primeng/splitbutton';
import { ImageModule } from 'primeng/image';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { RegisterComponent } from './register/register.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { FloatLabelModule } from 'primeng/floatlabel';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { CookieService } from 'ngx-cookie-service';
import { HomeComponent } from './home/home.component';
import { MessageService } from 'primeng/api';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { UploadComponent } from './upload/upload.component';
import { FileUploadModule } from 'primeng/fileupload';
import { BadgeModule } from 'primeng/badge';
import { TableModule } from 'primeng/table';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    RegisterComponent,
    NotFoundComponent,
    HomeComponent,
    DashboardComponent,
    UploadComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    ButtonModule,
    CardModule,
    ToolbarModule,
    SplitButtonModule,
    ImageModule,
    InputTextModule,
    PasswordModule,
    FloatLabelModule,
    MessagesModule,
    MessageModule,
    ToastModule,
    ProgressSpinnerModule,
    FileUploadModule,
    BadgeModule,
    TableModule
  ],
  providers: [
    provideHttpClient(withFetch()),
    CookieService,
    MessageService

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
