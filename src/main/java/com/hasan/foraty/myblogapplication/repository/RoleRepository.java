package com.hasan.foraty.myblogapplication.repository;

import com.hasan.foraty.myblogapplication.entity.Roles;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Roles,Long> {
  Optional<Roles> findByName(String name);
}
