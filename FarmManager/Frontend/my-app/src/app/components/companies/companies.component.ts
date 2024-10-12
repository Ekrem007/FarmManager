import { Component, OnInit } from '@angular/core';
import { Company } from '../../models/company';
import { CompanyService } from '../../services/company.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-companies',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit {
  companies: Company[] = [];
  newCompanyName: string = '';
  selectedCompany: Company | null = null;

  constructor(private companyService: CompanyService) {}

  ngOnInit(): void {
    this.getCompanies();
    this.calculateTotalEarnings();
  }

  getCompanies(): void {
    this.companyService.getCompanies().subscribe((data: Company[]) => {
      this.companies = data;
      this.calculateTotalEarnings();
    });
  }

  calculateTotalEarnings(): void {
    this.companies.forEach(company => {
      this.companyService.getSalesForCompany(company.name).subscribe(salesData => {
        const totalEarnings = salesData.reduce((acc, sale) => acc + sale.totalSum, 0);
        company.totalEarnings = totalEarnings;
      });
    });
  }

  addCompany(): void {
    if (this.newCompanyName) {
      const newCompany: Company = {
        id: 0, 
        name: this.newCompanyName,
        salesIds: [], 
      };
  
      this.companyService.addCompany(newCompany).subscribe({
        next: (company: Company) => {
          this.companies.push(company);
          this.newCompanyName = '';
          this.calculateTotalEarnings();
        },
        error: (err) => {
          console.error('Şirket ekleme başarısız:', err);
        }
      });
    }
  }

  editCompany(company: Company): void {
    this.selectedCompany = { ...company }; 
  }

  updateCompany(): void {
    if (this.selectedCompany) {
      this.companyService.updateCompany(this.selectedCompany.id, this.selectedCompany).subscribe({
        next: () => {
          this.getCompanies(); 
          this.selectedCompany = null;
        },
        error: (err) => {
          console.error('Şirket güncelleme başarısız:', err);
        }
      });
    }
  }

  deleteCompany(id: number): void {
    if (confirm("Bu şirketi silmek istediğinize emin misiniz?")) {
      this.companyService.deleteCompany(id).subscribe({
        next: (response: string) => {
          console.log(response);
          alert("Şirket başarıyla silindi.");
          this.companies = this.companies.filter(company => company.id !== id);
        },
        error: (err) => {
          console.error("Silme işlemi başarısız oldu:", err);
          alert("Şirket silinemedi, lütfen tekrar deneyin.");
        }
      });
    }
  }

  cancelEdit(): void {
    this.selectedCompany = null; 
  }
}
