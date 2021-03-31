package com.toptal.timemgmt.repository;

import com.toptal.timemgmt.model.Role;
import com.toptal.timemgmt.model.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName roleName);
}
