import { NgModule } from '@angular/core';
import { mapToCanActivate, RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './auth/auth-guard';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UploadComponent } from './upload/upload.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', canActivate: mapToCanActivate([AuthGuard]), component: LoginComponent,  },
  { path: 'register',canActivate: mapToCanActivate([AuthGuard]),  component: RegisterComponent },
  { path: 'dashboard', canActivate: mapToCanActivate([AuthGuard]), component: DashboardComponent},
  { path: 'upload', canActivate: mapToCanActivate([AuthGuard]), component: UploadComponent},
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
