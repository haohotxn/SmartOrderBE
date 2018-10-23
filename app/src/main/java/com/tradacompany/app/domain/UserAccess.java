package com.tradacompany.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import com.tradacompany.app.domain.enumeration.Role;

/**
 * A UserAccess.
 */
@Document(collection = "user_access")
public class UserAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("user_name")
    private String userName;

    @Field("pass_word")
    private String passWord;

    @Field("role")
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public UserAccess userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public UserAccess passWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Role getRole() {
        return role;
    }

    public UserAccess role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccess userAccess = (UserAccess) o;
        if (userAccess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccess.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccess{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", passWord='" + getPassWord() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
