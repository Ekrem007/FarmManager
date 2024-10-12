import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { NgModule } from '@angular/core';
import { CommunicationComponent } from './components/communication/communication.component';
import { CompaniesComponent } from './components/companies/companies.component';
import { EmployeesComponent } from './components/employees/employees.component';
import { ProductsComponent } from './components/products/products.component';
import { SalesComponent } from './components/sales/sales.component';
import { ExpansesComponent } from './components/expanses/expanses.component';
import { ReportComponent } from './components/report/report.component';
import { SalaryComponent } from './components/salary/salary.component';
import { CompanySalesComponent } from './components/company-sales/company-sales.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },  
    { path: 'main', component: MainPageComponent },  
    { path: 'communication', component: CommunicationComponent }, 
    { path: 'companies', component: CompaniesComponent },   
    { path: 'employees', component: EmployeesComponent }, 
    { path: 'products', component: ProductsComponent },
    { path: 'sales', component: SalesComponent },   
    { path: 'expanses', component: ExpansesComponent }, 
    { path: 'report', component: ReportComponent },   
    { path: 'employees/:id', component: SalaryComponent },
    { path: 'companies/:id', component: CompanySalesComponent },
    
   
    { path: '**', redirectTo: '/login' }
  ];
  @NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule]
})
export class AppRoutingModule{}
