import { Product } from './product';

export interface Sale {
    id:number;
    totalSum:number;
    productName:string
    quantity:number;
    companyName:string
    companyId?:number,
    price:number;
    date?:Date;
    createdAt?:Date
    updatedAt?:Date
    product?:Product;


}
