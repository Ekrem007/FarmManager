import { HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LognService } from '../../services/login.service';
import { CommonModule } from '@angular/common';
import{FormsModule} from "@angular/forms"

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule,],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private loginService: LognService, private router: Router){}

  onLogin() {
    if (this.username && this.password) {
      this.loginService.login(this.username, this.password).subscribe(
        response => {
          alert(response);
          if (response === "Login successful!") {
            this.router.navigateByUrl('main');  
          }
        },
        error => {
          console.error('Error:', error);
          alert('Login failed!');
        }
      );
    } else {
      alert('Please enter both username and password.');
    }
  }

}
