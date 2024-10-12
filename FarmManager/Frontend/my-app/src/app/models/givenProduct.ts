export interface GivenProduct {
    id: number;
    productId: number;
    companyId: number;
    stock: number;
    createdAt?: Date;
    updatedAt?: Date;
    productName?: string;  
    companyName?: string;  
}
