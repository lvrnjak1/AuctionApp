package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Role;
import ba.abh.AuctionApp.domain.enums.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRole(RoleName roleName);
}
