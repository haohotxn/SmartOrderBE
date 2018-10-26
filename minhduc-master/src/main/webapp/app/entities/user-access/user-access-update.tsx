import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './user-access.reducer';
import { IUserAccess } from 'app/shared/model/user-access.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserAccessUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserAccessUpdateState {
  isNew: boolean;
}

export class UserAccessUpdate extends React.Component<IUserAccessUpdateProps, IUserAccessUpdateState> {
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
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { userAccessEntity } = this.props;
      const entity = {
        ...userAccessEntity,
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
    this.props.history.push('/entity/user-access');
  };

  render() {
    const { userAccessEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartorderApp.userAccess.home.createOrEditLabel">
              <Translate contentKey="smartorderApp.userAccess.home.createOrEditLabel">Create or edit a UserAccess</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userAccessEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="user-access-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="userNameLabel" for="userName">
                    <Translate contentKey="smartorderApp.userAccess.userName">User Name</Translate>
                  </Label>
                  <AvField id="user-access-userName" type="text" name="userName" />
                </AvGroup>
                <AvGroup>
                  <Label id="passWordLabel" for="passWord">
                    <Translate contentKey="smartorderApp.userAccess.passWord">Pass Word</Translate>
                  </Label>
                  <AvField id="user-access-passWord" type="text" name="passWord" />
                </AvGroup>
                <AvGroup>
                  <Label id="roleLabel">
                    <Translate contentKey="smartorderApp.userAccess.role">Role</Translate>
                  </Label>
                  <AvInput
                    id="user-access-role"
                    type="select"
                    className="form-control"
                    name="role"
                    value={(!isNew && userAccessEntity.role) || 'ADMIN'}
                  >
                    <option value="ADMIN">
                      <Translate contentKey="smartorderApp.Role.ADMIN" />
                    </option>
                    <option value="EMPLOYEE">
                      <Translate contentKey="smartorderApp.Role.EMPLOYEE" />
                    </option>
                    <option value="CUSTOMER">
                      <Translate contentKey="smartorderApp.Role.CUSTOMER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-access" replace color="info">
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
  userAccessEntity: storeState.userAccess.entity,
  loading: storeState.userAccess.loading,
  updating: storeState.userAccess.updating,
  updateSuccess: storeState.userAccess.updateSuccess
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
)(UserAccessUpdate);
