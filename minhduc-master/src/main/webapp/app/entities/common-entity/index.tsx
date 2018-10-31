import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommonEntity from './common-entity';
import CommonEntityDetail from './common-entity-detail';
import CommonEntityUpdate from './common-entity-update';
import CommonEntityDeleteDialog from './common-entity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommonEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommonEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommonEntityDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommonEntity} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CommonEntityDeleteDialog} />
  </>
);

export default Routes;
