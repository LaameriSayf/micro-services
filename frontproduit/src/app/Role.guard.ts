import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    const requiredRole = next.data['role'];  // Get the role from the route data
    const userRoles = this.authService.getUserRoles();  // Get the roles of the currently logged-in user

    if (userRoles && userRoles.includes(requiredRole)) {
      return true;  // User has the required role, grant access
    } else {
      this.router.navigate(['/login']);  // Redirect to login page if the user doesn't have the required role
      return false;
    }
  }
}
