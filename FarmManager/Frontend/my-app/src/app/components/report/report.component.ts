import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Sale } from '../../models/sale';
import { ExpanseType } from '../../models/expanseType';
import { Expanse } from '../../models/expanse';
import { ExpanseService } from '../../services/expanse.service';
import { SaleService } from '../../services/sale.service';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent implements OnInit {
  sales: Sale[] = [];
  expanses: Expanse[] = [];
  filterExpanseType: ExpanseType | undefined;
  filteredExpanses: Expanse[] = [];
  filteredSales: Sale[] = [];
  expanseTypes = Object.values(ExpanseType);
  selectedExpanse: Expanse | null = null;
  startDate: string | undefined;
  endDate: string | undefined;
  totalRevenue: number = 0; 
  totalExpense: number = 0; 
  balance: number = 0; 

  constructor(private expanseService:ExpanseService, private saleService:SaleService){}
  ngOnInit(): void {
    this.getSales();
    this.getExpanses();
  }

  getSales(): void {
    this.saleService.getSales().subscribe((data: Sale[]) => {
      this.sales = data;
      this.calculateFilteredRevenue(); 
      this.calculateFilteredBalance() 
    });
  }

  getExpanses(): void {
    this.expanseService.getExpanses().subscribe((data: Expanse[]) => {
      this.expanses = data;
      this.calculateFilteredExpense(); 
      this.calculateFilteredBalance() 
    });
  }
  
calculateFilteredRevenue(): void {
  this.totalRevenue = this.filteredSales.reduce((sum, sale) => sum + sale.totalSum, 0);
}


calculateFilteredExpense(): void {
  this.totalExpense = this.filteredExpanses.reduce((sum, expanse) => sum + expanse.amount, 0);
}


calculateFilteredBalance(): void {
  this.balance = this.totalRevenue - this.totalExpense;
}


  filterData(): void {
    if (this.startDate && this.endDate) {
      const start = new Date(this.startDate);
      const end = new Date(this.endDate);
  
     
      this.filteredSales = this.sales.filter(sale => {
        const saleDate = new Date(); 
        return saleDate >= start && saleDate <= end;
      });
  
     
      this.filteredExpanses = this.expanses.filter(expanse => {
        const expanseDate = new Date(expanse.createdAt || expanse.updatedAt); 
        return expanseDate >= start && expanseDate <= end;
      });
    } else {
     
      this.filteredSales = this.sales;
      this.filteredExpanses = this.expanses;
    }
  
    
    this.calculateFilteredRevenue();
    this.calculateFilteredExpense();
    this.calculateFilteredBalance();
  }
  
}
