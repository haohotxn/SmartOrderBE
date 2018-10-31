import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserAccess, defaultValue } from 'app/shared/model/user-access.model';

export const ACTION_TYPES = {
  FETCH_USERACCESS_LIST: 'userAccess/FETCH_USERACCESS_LIST',
  FETCH_USERACCESS: 'userAccess/FETCH_USERACCESS',
  CREATE_USERACCESS: 'userAccess/CREATE_USERACCESS',
  UPDATE_USERACCESS: 'userAccess/UPDATE_USERACCESS',
  DELETE_USERACCESS: 'userAccess/DELETE_USERACCESS',
  RESET: 'userAccess/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserAccess>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UserAccessState = Readonly<typeof initialState>;

// Reducer

export default (state: UserAccessState = initialState, action): UserAccessState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERACCESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERACCESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERACCESS):
    case REQUEST(ACTION_TYPES.UPDATE_USERACCESS):
    case REQUEST(ACTION_TYPES.DELETE_USERACCESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERACCESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERACCESS):
    case FAILURE(ACTION_TYPES.CREATE_USERACCESS):
    case FAILURE(ACTION_TYPES.UPDATE_USERACCESS):
    case FAILURE(ACTION_TYPES.DELETE_USERACCESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERACCESS_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERACCESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERACCESS):
    case SUCCESS(ACTION_TYPES.UPDATE_USERACCESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERACCESS):
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

const apiUrl = 'api/user-accesses';

// Actions

export const getEntities: ICrudGetAllAction<IUserAccess> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERACCESS_LIST,
    payload: axios.get<IUserAccess>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUserAccess> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERACCESS,
    payload: axios.get<IUserAccess>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserAccess> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERACCESS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUserAccess> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERACCESS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserAccess> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERACCESS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
