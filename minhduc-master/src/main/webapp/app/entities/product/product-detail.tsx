import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProductDetail extends React.Component<IProductDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { productEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="smartorderApp.product.detail.title">Product</Translate> [<b>{productEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="smartorderApp.product.name">Name</Translate>
              </span>
            </dt>
            <dd>{productEntity.name}</dd>
            <dt>
              <span id="image">
                <Translate contentKey="smartorderApp.product.image">Image</Translate>
              </span>
            </dt>
            <dd>{productEntity.image}</dd>
            <dt>
              <span id="price">
                <Translate contentKey="smartorderApp.product.price">Price</Translate>
              </span>
            </dt>
            <dd>{productEntity.price}</dd>
            <dt>
              <span id="catalog">
                <Translate contentKey="smartorderApp.product.catalog">Catalog</Translate>
              </span>
            </dt>
            <dd>{productEntity.catalog}</dd>
            <dt>
              <span id="vote">
                <Translate contentKey="smartorderApp.product.vote">Vote</Translate>
              </span>
            </dt>
            <dd>{productEntity.vote}</dd>
            <dt>
              <span id="rate">
                <Translate contentKey="smartorderApp.product.rate">Rate</Translate>
              </span>
            </dt>
            <dd>{productEntity.rate}</dd>
            <dt>
              <span id="count">
                <Translate contentKey="smartorderApp.product.count">Count</Translate>
              </span>
            </dt>
            <dd>{productEntity.count}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="smartorderApp.product.status">Status</Translate>
              </span>
            </dt>
            <dd>{productEntity.status}</dd>
          </dl>
          <Button tag={Link} to="/entity/product" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/product/${productEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ product }: IRootState) => ({
  productEntity: product.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductDetail);
