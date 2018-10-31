export const enum Catalog {
  FOOD = 'FOOD',
  DRINK = 'DRINK',
  OTHER = 'OTHER'
}

export const enum StatusProduct {
  OUT_OF_STOCK = 'OUT_OF_STOCK',
  STOCKING = 'STOCKING',
  AVAILABLE = 'AVAILABLE'
}

export interface IProduct {
  id?: string;
  name?: string;
  image?: string;
  price?: number;
  catalog?: Catalog;
  vote?: number;
  rate?: number;
  count?: number;
  status?: StatusProduct;
}

export const defaultValue: Readonly<IProduct> = {};
