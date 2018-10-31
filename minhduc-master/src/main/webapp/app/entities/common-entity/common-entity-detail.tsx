import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './common-entity.reducer';
import { ICommonEntity } from 'app/shared/model/common-entity.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICommonEntityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CommonEntityDetail extends React.Component<ICommonEntityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { commonEntityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="smartorderApp.commonEntity.detail.title">CommonEntity</Translate> [<b>{commonEntityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="createdDate">
                <Translate contentKey="smartorderApp.commonEntity.createdDate">Created Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={commonEntityEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="lastModifiedDate">
                <Translate contentKey="smartorderApp.commonEntity.lastModifiedDate">Last Modified Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={commonEntityEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/common-entity" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/common-entity/${commonEntityEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ commonEntity }: IRootState) => ({
  commonEntityEntity: commonEntity.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CommonEntityDetail);
