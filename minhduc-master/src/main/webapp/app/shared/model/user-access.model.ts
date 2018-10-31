export const enum Role {
  ADMIN = 'ADMIN',
  EMPLOYEE = 'EMPLOYEE',
  CUSTOMER = 'CUSTOMER'
}

export interface IUserAccess {
  id?: string;
  userName?: string;
  passWord?: string;
  role?: Role;
}

export const defaultValue: Readonly<IUserAccess> = {};
