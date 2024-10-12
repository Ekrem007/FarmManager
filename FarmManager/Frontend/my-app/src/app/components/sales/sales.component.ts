import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Sale } from '../../models/sale';
import { Product } from '../../models/product';
import { Company } from '../../models/company';
import { SaleService } from './../../services/sale.service';

@Component({
  selector: 'app-sales',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {
  sales: Sale[] = [];
  products: Product[] = [];
  companies: Company[] = [];

  selectedProductName: string | null = null;
  selectedCompanyName: string | null = null;
  newSaleQuantity: number | null = null;
  newSalePrice: number | null = null;
  selectedSale: Sale | null = null;


  constructor(private saleService: SaleService) {}

  ngOnInit(): void {
    this.getSales();
    this.getProducts();
    this.getCompanies();
  }

  getSales(): void {
    this.saleService.getSales().subscribe((data: Sale[]) => {
      this.sales = data;
    });
  }

  getProducts(): void {
    this.saleService.getProducts().subscribe((data: Product[]) => {
      this.products = data;
    });
  }

  getCompanies(): void {
    this.saleService.getCompanies().subscribe((data: Company[]) => {
      this.companies = data;
    });
  }

  deleteSale(id: number): void {
    if (confirm("Bu satışı silmek istediğinize emin misiniz?")) {
      this.saleService.deleteSale(id).subscribe({
        next: (response: string) => {
          console.log(response);
          alert("Satış başarıyla silindi.");
          this.sales = this.sales.filter(sale => sale.id !== id);
        },
        error: (err) => {
          console.error("Silme işlemi başarısız oldu:", err);
          alert("Satış silinemedi, lütfen tekrar deneyin.");
        }
      });
    }
  }

  addSale(): void {
    if (!this.selectedProductName || !this.selectedCompanyName || this.newSaleQuantity === null || this.newSaleQuantity <= 0 || this.newSalePrice === null || this.newSalePrice <= 0) {
      alert("Lütfen tüm alanları doldurun ve geçerli değerler girin.");
      return;
    }

    const selectedProduct = this.products.find(p => p.name === this.selectedProductName);
    const selectedCompany = this.companies.find(c => c.name === this.selectedCompanyName);

    if (!selectedProduct || !selectedCompany) {
      alert("Ürün veya şirket bulunamadı.");
      return;
    }

    const newSale: Sale = {
      id: 0,
      totalSum: this.newSaleQuantity * this.newSalePrice,
      productName: this.selectedProductName,
      quantity: this.newSaleQuantity,
      companyName: this.selectedCompanyName,
      price: this.newSalePrice,
      product: selectedProduct
    };

    this.saleService.addSale(newSale).subscribe(() => {
      this.sales.push(newSale);
      this.resetForm();
      this.getSales();
    });
  }

  resetForm(): void {
    this.selectedProductName = null;
    this.selectedCompanyName = null;
    this.newSaleQuantity = null;
    this.newSalePrice = null;
  }
  editSale(sale: Sale): void {
    this.selectedSale = {
      ...sale,
      productName: sale.product?.name || '', 
      companyName: sale.companyName || ''
    };
  }
  



  updateSale(): void {
    if (this.selectedSale) {
      const updatedSale: Partial<Sale> = {
        productName: this.selectedSale.productName,
        companyName: this.selectedSale.companyName,
        quantity: this.selectedSale.quantity,
        price: this.selectedSale.price,
        totalSum: this.selectedSale.quantity * this.selectedSale.price
      };

      this.saleService.updateSale(this.selectedSale.id, updatedSale).subscribe(() => {
        alert('Satış başarıyla güncellendi.');
        this.getSales(); 
        this.selectedSale = null; 
      });
    }
  }

  cancelEdit(): void {
    this.selectedSale = null; 
  }
}
