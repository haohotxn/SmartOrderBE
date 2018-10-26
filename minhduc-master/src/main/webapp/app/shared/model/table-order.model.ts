export const enum StatusTable {
  EMPTY = 'EMPTY',
  FULL = 'FULL'
}

export interface ITableOrder {
  id?: string;
  name?: string;
  status?: StatusTable;
  accessTableCode?: string;
}

export const defaultValue: Readonly<ITableOrder> = {};
