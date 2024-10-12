import { ExpanseType } from "./expanseType";
export interface Expanse {
  id: number;
  expanseType: ExpanseType; 
  amount: number;
  description: string;
  createdAt: Date;
  updatedAt: Date;
}
