import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserAccess from './user-access';
import UserAccessDetail from './user-access-detail';
import UserAccessUpdate from './user-access-update';
import UserAccessDeleteDialog from './user-access-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserAccessUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserAccessUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserAccessDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserAccess} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserAccessDeleteDialog} />
  </>
);

export default Routes;
