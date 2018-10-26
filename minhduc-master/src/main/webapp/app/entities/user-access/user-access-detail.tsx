import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-access.reducer';
import { IUserAccess } from 'app/shared/model/user-access.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserAccessDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserAccessDetail extends React.Component<IUserAccessDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userAccessEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="smartorderApp.userAccess.detail.title">UserAccess</Translate> [<b>{userAccessEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="userName">
                <Translate contentKey="smartorderApp.userAccess.userName">User Name</Translate>
              </span>
            </dt>
            <dd>{userAccessEntity.userName}</dd>
            <dt>
              <span id="passWord">
                <Translate contentKey="smartorderApp.userAccess.passWord">Pass Word</Translate>
              </span>
            </dt>
            <dd>{userAccessEntity.passWord}</dd>
            <dt>
              <span id="role">
                <Translate contentKey="smartorderApp.userAccess.role">Role</Translate>
              </span>
            </dt>
            <dd>{userAccessEntity.role}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-access" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-access/${userAccessEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ userAccess }: IRootState) => ({
  userAccessEntity: userAccess.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserAccessDetail);
