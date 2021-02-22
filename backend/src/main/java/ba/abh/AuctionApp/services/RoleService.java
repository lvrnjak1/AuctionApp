package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Role;
import ba.abh.AuctionApp.domain.enums.RoleName;
import ba.abh.AuctionApp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    Role findByRoleName(final RoleName roleName) {
        return roleRepository.findByRole(roleName).orElseThrow();
    }
}
