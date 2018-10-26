import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './common-entity.reducer';
import { ICommonEntity } from 'app/shared/model/common-entity.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommonEntityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICommonEntityUpdateState {
  isNew: boolean;
}

export class CommonEntityUpdate extends React.Component<ICommonEntityUpdateProps, ICommonEntityUpdateState> {
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
    values.createdDate = new Date(values.createdDate);
    values.lastModifiedDate = new Date(values.lastModifiedDate);

    if (errors.length === 0) {
      const { commonEntityEntity } = this.props;
      const entity = {
        ...commonEntityEntity,
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
    this.props.history.push('/entity/common-entity');
  };

  render() {
    const { commonEntityEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartorderApp.commonEntity.home.createOrEditLabel">
              <Translate contentKey="smartorderApp.commonEntity.home.createOrEditLabel">Create or edit a CommonEntity</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : commonEntityEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="common-entity-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="createdDateLabel" for="createdDate">
                    <Translate contentKey="smartorderApp.commonEntity.createdDate">Created Date</Translate>
                  </Label>
                  <AvInput
                    id="common-entity-createdDate"
                    type="datetime-local"
                    className="form-control"
                    name="createdDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.commonEntityEntity.createdDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="lastModifiedDateLabel" for="lastModifiedDate">
                    <Translate contentKey="smartorderApp.commonEntity.lastModifiedDate">Last Modified Date</Translate>
                  </Label>
                  <AvInput
                    id="common-entity-lastModifiedDate"
                    type="datetime-local"
                    className="form-control"
                    name="lastModifiedDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.commonEntityEntity.lastModifiedDate)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/common-entity" replace color="info">
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
  commonEntityEntity: storeState.commonEntity.entity,
  loading: storeState.commonEntity.loading,
  updating: storeState.commonEntity.updating,
  updateSuccess: storeState.commonEntity.updateSuccess
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
)(CommonEntityUpdate);
