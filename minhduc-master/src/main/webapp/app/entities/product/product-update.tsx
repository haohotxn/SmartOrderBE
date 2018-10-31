import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProductUpdateState {
  isNew: boolean;
}

export class ProductUpdate extends React.Component<IProductUpdateProps, IProductUpdateState> {
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
      const { productEntity } = this.props;
      const entity = {
        ...productEntity,
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
    this.props.history.push('/entity/product');
  };

  render() {
    const { productEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartorderApp.product.home.createOrEditLabel">
              <Translate contentKey="smartorderApp.product.home.createOrEditLabel">Create or edit a Product</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : productEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="product-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="smartorderApp.product.name">Name</Translate>
                  </Label>
                  <AvField id="product-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="imageLabel" for="image">
                    <Translate contentKey="smartorderApp.product.image">Image</Translate>
                  </Label>
                  <AvField id="product-image" type="text" name="image" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    <Translate contentKey="smartorderApp.product.price">Price</Translate>
                  </Label>
                  <AvField id="product-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="catalogLabel">
                    <Translate contentKey="smartorderApp.product.catalog">Catalog</Translate>
                  </Label>
                  <AvInput
                    id="product-catalog"
                    type="select"
                    className="form-control"
                    name="catalog"
                    value={(!isNew && productEntity.catalog) || 'FOOD'}
                  >
                    <option value="FOOD">
                      <Translate contentKey="smartorderApp.Catalog.FOOD" />
                    </option>
                    <option value="DRINK">
                      <Translate contentKey="smartorderApp.Catalog.DRINK" />
                    </option>
                    <option value="OTHER">
                      <Translate contentKey="smartorderApp.Catalog.OTHER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="voteLabel" for="vote">
                    <Translate contentKey="smartorderApp.product.vote">Vote</Translate>
                  </Label>
                  <AvField id="product-vote" type="string" className="form-control" name="vote" />
                </AvGroup>
                <AvGroup>
                  <Label id="rateLabel" for="rate">
                    <Translate contentKey="smartorderApp.product.rate">Rate</Translate>
                  </Label>
                  <AvField id="product-rate" type="string" className="form-control" name="rate" />
                </AvGroup>
                <AvGroup>
                  <Label id="countLabel" for="count">
                    <Translate contentKey="smartorderApp.product.count">Count</Translate>
                  </Label>
                  <AvField id="product-count" type="string" className="form-control" name="count" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="smartorderApp.product.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="product-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && productEntity.status) || 'OUT_OF_STOCK'}
                  >
                    <option value="OUT_OF_STOCK">
                      <Translate contentKey="smartorderApp.StatusProduct.OUT_OF_STOCK" />
                    </option>
                    <option value="STOCKING">
                      <Translate contentKey="smartorderApp.StatusProduct.STOCKING" />
                    </option>
                    <option value="AVAILABLE">
                      <Translate contentKey="smartorderApp.StatusProduct.AVAILABLE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/product" replace color="info">
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
  productEntity: storeState.product.entity,
  loading: storeState.product.loading,
  updating: storeState.product.updating,
  updateSuccess: storeState.product.updateSuccess
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
)(ProductUpdate);
