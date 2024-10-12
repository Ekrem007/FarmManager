import { GivenProductService } from './../../services/given-product.service';
import { GivenProduct } from './../../models/givenProduct';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Sale } from '../../models/sale';
import { Product } from '../../models/product';
import { Company } from '../../models/company';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { SaleService } from '../../services/sale.service';
import { CompanyService } from '../../services/company.service';
import { ProductService } from '../../services/product.service';
import { SalesUpdateDTO } from '../../models/updateSaleDto';

@Component({
  selector: 'app-company-sales',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterLink],
  templateUrl: './company-sales.component.html',
  styleUrl: './company-sales.component.css'
})
export class CompanySalesComponent implements OnInit {

  sales: Sale[] = [];
  products: Product[] = [];
  companies: Company[] = [];
  selectedGivenProduct: GivenProduct | null = null;
  selectedProductName: string | null = null;
  selectedCompanyName: string | null = null;
  newSaleQuantity: number | null = null;
  newSalePrice: number | null = null;
  selectedSale: Sale | null = null;
  companyName: string = ''; 
  companyId: number = 0;
  productId: number = 0; 
  stock: number = 0;     
  givenProducts: GivenProduct[] = [];




  constructor(
    private route:ActivatedRoute,
    private givenProductService: GivenProductService,
    private saleService:SaleService,
    private companyService: CompanyService,
    private productService:ProductService
   ){}


  ngOnInit(): void {
    this.companyId = +this.route.snapshot.paramMap.get('id')!;
    this.loadCompanyName();
    this.loadGivenProducts();
    this.getSalesByCompanyId();
    this.getProducts();
    
  }
  loadCompanyName(): void {
    this.companyService.getCompanyById(this.companyId).subscribe({
      next: (company: Company) => {
        this.companyName = company.name; 
      },
      error: (error) => {
        console.error('Error fetching company', error);
      }
    });
  }
  getProducts(): void {
    this.productService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data; 
      },
      error: (error) => {
        console.error('Error fetching products', error);
      },
    });
  }

  loadGivenProducts(): void {
    this.givenProductService.getGivenProductsByCompanyId(this.companyId).subscribe({
      next: (data: GivenProduct[]) => {
        this.givenProducts = data;
      },
      error: (error) => {
        console.error('Error fetching given products', error);
      }
    });
  }
  
  getSalesByCompanyId(): void {
    this.givenProductService.getSalesByCompanyId(this.companyId).subscribe((data: Sale[]) => {
      this.sales = data;
    });
  }
  addGivenProduct(): void {
    if (this.productId && this.stock) {
      const newGivenProduct: Omit<GivenProduct, 'id'> = {
        companyId: this.companyId,
        productId: this.productId,
        stock: this.stock
      };
  
      this.givenProductService.addGivenProduct(newGivenProduct).subscribe({
        next: (response) => {
          console.log('Product added successfully:', response);
          this.loadGivenProducts();  
        },
        error: (error) => {
          console.error('Error adding product:', error);
        }
      });
    } else {
      console.warn('Product ID and stock must be provided');
    }
  }
  
  
  deleteGivenProduct(givenProductId: number): void {
    this.givenProductService.deleteGivenProduct(givenProductId).subscribe({
      next: () => {
        
        this.loadGivenProducts();
      },
      error: (error) => {
        
        console.error('Maaş silinirken hata oluştu:', error);
        
        
        alert('Maaş silinirken bir hata oluştu. Lütfen tekrar deneyin.');
      }
    });
  }
  openEditModal(givenProduct: GivenProduct): void {
    this.selectedGivenProduct = { ...givenProduct };
  }

  cancelEdit(): void {
    this.selectedGivenProduct = null;
  }
  updateGivenProduct(): void {
    if (this.selectedGivenProduct) {
      const updatedProduct = {
        stock: this.selectedGivenProduct.stock,
        productId: this.selectedGivenProduct.productId,
        companyId: this.selectedGivenProduct.companyId
      };

      this.givenProductService.updateGivenProduct(this.selectedGivenProduct.id, updatedProduct)
        .subscribe({
          next: (response) => {
            
            console.log('Güncellenmiş Ürün:', response);
            this.loadGivenProducts(); 
            this.cancelEdit(); 
          },
          error: (error) => {
            console.error('Güncelleme Hatası:', error);
            alert('Güncelleme başarısız oldu. Lütfen tekrar deneyin.');
          }
        });
    }
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
    if (this.selectedProductName && this.newSaleQuantity != null && this.newSalePrice != null) {
      const newSale: Sale = {
        id: 0, 
        totalSum: this.newSaleQuantity * this.newSalePrice!,
        productName: this.selectedProductName,
        quantity: this.newSaleQuantity,
        companyName: this.companyName, 
        companyId: this.companyId,
        price: this.newSalePrice,
        createdAt: new Date(),
        updatedAt: new Date()
      };

      this.saleService.addSale(newSale).subscribe({
        next: (response) => {
          console.log('Sale added successfully:', response);
          this.sales.push(response); 
          
        },
        error: (error) => {
          console.error('Error adding sale:', error);
          alert('Satış eklenirken bir hata oluştu. Lütfen tekrar deneyin.');
        }
      });
    } else {
      console.warn('Tüm alanları doldurduğunuzdan emin olun.');
    }
  }
  openSaleEditModal(sale: Sale) {
    
    this.selectedSale = {
      ...sale,
      product: sale.product ? { ...sale.product } : undefined
    };
  }
  
  
  updateSale(): void {
    if (this.selectedSale) {
      const salesUpdate: SalesUpdateDTO = {
        quantity: this.selectedSale.quantity,
        price: this.selectedSale.price,
        productName: this.selectedSale.product?.name ?? '',
        companyName: this.companyName 
      };
  
      this.givenProductService.updateSales(this.selectedSale.id, salesUpdate).subscribe({
        next: (response) => {
          console.log('Güncelleme başarılı:', response);
          
          this.getSalesByCompanyId();
          this.cancelSaleEdit(); 
        },
        error: (error) => {
          console.error('Güncelleme hatası:', error);
          alert('Güncelleme sırasında bir hata oluştu. Lütfen tekrar deneyin.');
        }
      });
    }
  }
  
  cancelSaleEdit() {
    this.selectedSale = null;
  }  
  
  
  
}


   
