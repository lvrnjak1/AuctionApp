package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Role;
import ba.abh.AuctionApp.domain.enums.RoleName;
import ba.abh.AuctionApp.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    Role findByRoleName(RoleName roleName){
        return roleRepository.findByRole(roleName).orElseThrow();
    }
}
