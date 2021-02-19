package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Role;
import ba.abh.AuctionApp.domain.enums.RoleName;
import ba.abh.AuctionApp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    Role findByRoleName(RoleName roleName){
        return roleRepository.findByRole(roleName).orElseThrow();
    }
}
