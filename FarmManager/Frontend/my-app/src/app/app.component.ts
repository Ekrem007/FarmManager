
import { Component, LOCALE_ID, NgModule } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { NaviComponent } from './components/navi/navi.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EmployeesComponent } from './components/employees/employees.component';
import { registerLocaleData } from '@angular/common';



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,LoginComponent,NaviComponent,FormsModule,EmployeesComponent,ReactiveFormsModule],
  providers:[],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'my-app';
}
