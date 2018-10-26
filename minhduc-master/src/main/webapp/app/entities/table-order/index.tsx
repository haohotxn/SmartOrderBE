import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TableOrder from './table-order';
import TableOrderDetail from './table-order-detail';
import TableOrderUpdate from './table-order-update';
import TableOrderDeleteDialog from './table-order-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TableOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TableOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TableOrderDetail} />
      <ErrorBoundaryRoute path={match.url} component={TableOrder} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TableOrderDeleteDialog} />
  </>
);

export default Routes;
