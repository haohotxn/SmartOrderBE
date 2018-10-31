package org.itt.minhduc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.itt.minhduc.domain.enumeration.Role;

/**
 * A DTO for the UserAccess entity.
 */
public class UserAccessDTO implements Serializable {

    private String id;

    private String userName;

    private String passWord;

    private Role role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAccessDTO userAccessDTO = (UserAccessDTO) o;
        if (userAccessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccessDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", passWord='" + getPassWord() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
