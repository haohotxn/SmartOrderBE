import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './table-order.reducer';
import { ITableOrder } from 'app/shared/model/table-order.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITableOrderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TableOrderDetail extends React.Component<ITableOrderDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tableOrderEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="smartorderApp.tableOrder.detail.title">TableOrder</Translate> [<b>{tableOrderEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="smartorderApp.tableOrder.name">Name</Translate>
              </span>
            </dt>
            <dd>{tableOrderEntity.name}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="smartorderApp.tableOrder.status">Status</Translate>
              </span>
            </dt>
            <dd>{tableOrderEntity.status}</dd>
            <dt>
              <span id="accessTableCode">
                <Translate contentKey="smartorderApp.tableOrder.accessTableCode">Access Table Code</Translate>
              </span>
            </dt>
            <dd>{tableOrderEntity.accessTableCode}</dd>
          </dl>
          <Button tag={Link} to="/entity/table-order" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/table-order/${tableOrderEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tableOrder }: IRootState) => ({
  tableOrderEntity: tableOrder.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TableOrderDetail);
