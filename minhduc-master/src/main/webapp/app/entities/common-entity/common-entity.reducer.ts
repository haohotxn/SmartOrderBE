import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICommonEntity, defaultValue } from 'app/shared/model/common-entity.model';

export const ACTION_TYPES = {
  FETCH_COMMONENTITY_LIST: 'commonEntity/FETCH_COMMONENTITY_LIST',
  FETCH_COMMONENTITY: 'commonEntity/FETCH_COMMONENTITY',
  CREATE_COMMONENTITY: 'commonEntity/CREATE_COMMONENTITY',
  UPDATE_COMMONENTITY: 'commonEntity/UPDATE_COMMONENTITY',
  DELETE_COMMONENTITY: 'commonEntity/DELETE_COMMONENTITY',
  RESET: 'commonEntity/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICommonEntity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CommonEntityState = Readonly<typeof initialState>;

// Reducer

export default (state: CommonEntityState = initialState, action): CommonEntityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMMONENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMMONENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COMMONENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_COMMONENTITY):
    case REQUEST(ACTION_TYPES.DELETE_COMMONENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COMMONENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMMONENTITY):
    case FAILURE(ACTION_TYPES.CREATE_COMMONENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_COMMONENTITY):
    case FAILURE(ACTION_TYPES.DELETE_COMMONENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMONENTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMONENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMMONENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_COMMONENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMMONENTITY):
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

const apiUrl = 'api/common-entities';

// Actions

export const getEntities: ICrudGetAllAction<ICommonEntity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMMONENTITY_LIST,
  payload: axios.get<ICommonEntity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICommonEntity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMMONENTITY,
    payload: axios.get<ICommonEntity>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICommonEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMMONENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICommonEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMMONENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICommonEntity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMMONENTITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
