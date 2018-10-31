import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './table-order.reducer';
import { ITableOrder } from 'app/shared/model/table-order.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITableOrderUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITableOrderUpdateState {
  isNew: boolean;
}

export class TableOrderUpdate extends React.Component<ITableOrderUpdateProps, ITableOrderUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tableOrderEntity } = this.props;
      const entity = {
        ...tableOrderEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/table-order');
  };

  render() {
    const { tableOrderEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartorderApp.tableOrder.home.createOrEditLabel">
              <Translate contentKey="smartorderApp.tableOrder.home.createOrEditLabel">Create or edit a TableOrder</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tableOrderEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="table-order-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="smartorderApp.tableOrder.name">Name</Translate>
                  </Label>
                  <AvField id="table-order-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="smartorderApp.tableOrder.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="table-order-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && tableOrderEntity.status) || 'EMPTY'}
                  >
                    <option value="EMPTY">
                      <Translate contentKey="smartorderApp.StatusTable.EMPTY" />
                    </option>
                    <option value="FULL">
                      <Translate contentKey="smartorderApp.StatusTable.FULL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="accessTableCodeLabel" for="accessTableCode">
                    <Translate contentKey="smartorderApp.tableOrder.accessTableCode">Access Table Code</Translate>
                  </Label>
                  <AvField id="table-order-accessTableCode" type="text" name="accessTableCode" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/table-order" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  tableOrderEntity: storeState.tableOrder.entity,
  loading: storeState.tableOrder.loading,
  updating: storeState.tableOrder.updating,
  updateSuccess: storeState.tableOrder.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TableOrderUpdate);
