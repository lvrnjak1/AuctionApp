package ba.abh.AuctionApp.domain;

import ba.abh.AuctionApp.domain.enums.RoleName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    @NaturalId
    private RoleName role;

    public Role() {
    }

    public Role(RoleName role) {
        this.role = role;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role1 = (Role) o;
        return role == role1.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }
}
