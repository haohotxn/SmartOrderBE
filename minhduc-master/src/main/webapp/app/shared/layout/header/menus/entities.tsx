import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/common-entity">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.commonEntity" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/user-access">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.userAccess" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/invoice">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.invoice" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/product">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.product" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/table-order">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.tableOrder" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/order">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.order" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
