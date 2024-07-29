import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Injectable({providedIn: 'root'})
export class AuthGuard {
    constructor(private authService: AuthService) {}

    canActivate(route: ActivatedRouteSnapshot): boolean {
        let canActivate = false;
        const isLoggedIn = this.authService.isLoggedIn();
        route.url.forEach(path => {
            const url = path.path;
            if (url === '' ) canActivate = !isLoggedIn;
            else if (url === 'dashboard') canActivate = isLoggedIn;
            else if (url === 'upload') canActivate = isLoggedIn;
            else if (url === 'login') canActivate = !isLoggedIn;
            else if (url === 'register') canActivate = !isLoggedIn;
        });
        
        return canActivate;
    }
}
