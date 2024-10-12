
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import{Product} from '../../models/product';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit{

  products: Product[] = [];
  newProductName:string='';
  selectedProduct: Product | null = null;


  constructor(private productService:ProductService){}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(){
    this.productService.getProducts().subscribe((data:Product[])=>{
      this.products=data;
      this.calculateTotalEarnings();
    })

  }
  calculateTotalEarnings(): void {
    this.products.forEach(product => {
      this.productService.getSalesForProduct(product.name).subscribe(salesData => {
        const totalEarnings = salesData.reduce((acc, sale) => acc + sale.totalSum, 0);
        product.totalEarnings = totalEarnings;
      });
    });
  }

  addProduct():void{
    if(this.newProductName){
      const newProduct:Product={
        id:0,
        name:this.newProductName,
      };
      this.productService.addProduct(newProduct).subscribe({
        next: (product: Product) => {
          this.products.push(product);
          this.newProductName = '';
          this.getProducts();
          this.calculateTotalEarnings();
        },
        error: (err) => {
          console.error('Şirket ekleme başarısız:', err);
        }
      });

      }
    }
    deleteProduct(id: number): void {
      if (confirm("Bu şirketi silmek istediğinize emin misiniz?")) {
        this.productService.deleteProduct(id).subscribe({
          next: (response: string) => {
            console.log(response);
            alert("Şirket başarıyla silindi.");
            this.products = this.products.filter(product => product.id !== id);
            this.getProducts();
          },
          error: (err) => {
            console.error("Silme işlemi başarısız oldu:", err);
            alert("Şirket silinemedi, lütfen tekrar deneyin.");
          }
        });
      }
    }
    editProduct(product: Product): void {
      this.selectedProduct = { ...product }; 
    }
  
    updateProduct(): void {
      if (this.selectedProduct) {
        this.productService.updateProduct(this.selectedProduct.id, this.selectedProduct).subscribe({
          next: () => {
            this.getProducts(); 
            this.selectedProduct = null; 
          },
          error: (err) => {
            console.error('ürün güncelleme başarısız:', err);
          }
        });
      }
    }
    cancelEdit(): void {
      this.selectedProduct = null; 
    }
  }
