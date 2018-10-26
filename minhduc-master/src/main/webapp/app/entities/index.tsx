import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommonEntity from './common-entity';
import UserAccess from './user-access';
import Invoice from './invoice';
import Product from './product';
import TableOrder from './table-order';
import Order from './order';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/common-entity`} component={CommonEntity} />
      <ErrorBoundaryRoute path={`${match.url}/user-access`} component={UserAccess} />
      <ErrorBoundaryRoute path={`${match.url}/invoice`} component={Invoice} />
      <ErrorBoundaryRoute path={`${match.url}/product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}/table-order`} component={TableOrder} />
      <ErrorBoundaryRoute path={`${match.url}/order`} component={Order} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
