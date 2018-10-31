import { Moment } from 'moment';

export interface ICommonEntity {
  id?: string;
  createdDate?: Moment;
  lastModifiedDate?: Moment;
}

export const defaultValue: Readonly<ICommonEntity> = {};
