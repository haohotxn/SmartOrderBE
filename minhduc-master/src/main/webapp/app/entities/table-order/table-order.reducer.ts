import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITableOrder, defaultValue } from 'app/shared/model/table-order.model';

export const ACTION_TYPES = {
  FETCH_TABLEORDER_LIST: 'tableOrder/FETCH_TABLEORDER_LIST',
  FETCH_TABLEORDER: 'tableOrder/FETCH_TABLEORDER',
  CREATE_TABLEORDER: 'tableOrder/CREATE_TABLEORDER',
  UPDATE_TABLEORDER: 'tableOrder/UPDATE_TABLEORDER',
  DELETE_TABLEORDER: 'tableOrder/DELETE_TABLEORDER',
  RESET: 'tableOrder/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITableOrder>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TableOrderState = Readonly<typeof initialState>;

// Reducer

export default (state: TableOrderState = initialState, action): TableOrderState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TABLEORDER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TABLEORDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TABLEORDER):
    case REQUEST(ACTION_TYPES.UPDATE_TABLEORDER):
    case REQUEST(ACTION_TYPES.DELETE_TABLEORDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TABLEORDER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TABLEORDER):
    case FAILURE(ACTION_TYPES.CREATE_TABLEORDER):
    case FAILURE(ACTION_TYPES.UPDATE_TABLEORDER):
    case FAILURE(ACTION_TYPES.DELETE_TABLEORDER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TABLEORDER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TABLEORDER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TABLEORDER):
    case SUCCESS(ACTION_TYPES.UPDATE_TABLEORDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TABLEORDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/table-orders';

// Actions

export const getEntities: ICrudGetAllAction<ITableOrder> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TABLEORDER_LIST,
    payload: axios.get<ITableOrder>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITableOrder> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TABLEORDER,
    payload: axios.get<ITableOrder>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITableOrder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TABLEORDER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITableOrder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TABLEORDER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITableOrder> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TABLEORDER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
