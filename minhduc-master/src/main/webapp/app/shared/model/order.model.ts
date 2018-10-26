export const enum StatusOrder {
  REQUEST = 'REQUEST',
  INPROGESS = 'INPROGESS',
  PENDING = 'PENDING',
  CANCELED = 'CANCELED',
  COMPLETED = 'COMPLETED'
}

export interface IOrder {
  id?: string;
  status?: StatusOrder;
}

export const defaultValue: Readonly<IOrder> = {};
