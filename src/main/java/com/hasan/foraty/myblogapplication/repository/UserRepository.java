package com.hasan.foraty.myblogapplication.repository;

import com.hasan.foraty.myblogapplication.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
  Optional<Users> findByEmail(String email);
  Optional<Users> findByUsernameOrEmail(String email,String username);
}
