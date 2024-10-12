import { Expanse } from './../../models/expanse';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ExpanseService } from '../../services/expanse.service';
import { ExpanseType } from '../../models/expanseType';


@Component({
  selector: 'app-expanses',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './expanses.component.html',
  styleUrl: './expanses.component.css'
})
export class ExpansesComponent implements OnInit {


  expanses: Expanse[] = [];
  newDescription: string = '';
  newAmount: number = 0;
  selectedExpanseType: ExpanseType | undefined;
  expanseTypes = Object.values(ExpanseType);
  editingExpanse: Expanse | null = null;
  selectedExpanse: Expanse | null = null;
  startDate: string | undefined;
  endDate: string | undefined;
  filterExpanseType: ExpanseType | undefined;
  filteredExpanses: Expanse[] = [];
  totalAmount: number = 0;

  constructor(private expanseService:ExpanseService){}
  ngOnInit(): void {
    this.getExpanses()
    this.calculateTotalAmount()
  }

  getExpanses():void{
    this.expanseService.getExpanses().subscribe((data:Expanse[])=>{
      this.expanses=data;
    })

  }
  addExpanse(): void {
    if (this.newDescription && this.newAmount && this.selectedExpanseType) {
      const newExpanse: Expanse = {
        id: 0, 
        description: this.newDescription,
        amount: this.newAmount,
        expanseType: this.selectedExpanseType,
        createdAt: new Date(),
        updatedAt: new Date(),
      };

      this.expanseService.addExpanse(newExpanse).subscribe({
        next: (expanse: Expanse) => {
          this.expanses.push(expanse); 
          this.getExpanses()
          this.resetForm(); 
        },
        error: (err) => {
          console.error('Gider ekleme başarısız:', err);
        }
      });
    }
  }

  resetForm(): void {
    this.newDescription = '';
    this.newAmount = 0;
    this.selectedExpanseType = undefined;
  }

  deleteExpanse(id: number): void {
    this.expanseService.deleteExpanse(id).subscribe({
      next: (response) => {
        console.log('gider başarıyla silindi:', response);
        this.getExpanses();
       
     
      },
      error: (error) => {
        console.error('gider silinirken bir hata oluştu:', error);
      }
    });
  }
  updateExpanse(): void {
    if (this.selectedExpanse) {
      this.expanseService.updateExpanse(this.selectedExpanse).subscribe({
        next: () => {
          this.getExpanses();
          this.cancelEdit();
        },
        error: (error) => {
          console.error('Gider güncellenirken bir hata oluştu:', error);
        }
      });
    }
  }

  openEditModal(expanse: Expanse): void {
    this.selectedExpanse = { ...expanse };
  }

  cancelEdit(): void {
    this.selectedExpanse = null;
  }
  updateFilteredExpanses(): void {
    this.filteredExpanses = this.expanses.filter(exp => {
      const start = this.startDate ? new Date(this.startDate) : null;
      const end = this.endDate ? new Date(this.endDate) : null;
      const expDate = new Date(exp.createdAt || exp.updatedAt);
      expDate.setHours(0, 0, 0, 0);

      const isWithinDateRange = (!start || expDate >= start) &&
                                (!end || expDate <= end);
      const isCorrectType = !this.selectedExpanseType || exp.expanseType === this.selectedExpanseType;

      return isWithinDateRange && isCorrectType;
    });
    this.calculateTotalAmount();
  }
  calculateTotalAmount(): void {
    this.totalAmount = this.filteredExpanses.reduce((sum, exp) => sum + exp.amount, 0);
  }
  

  }

