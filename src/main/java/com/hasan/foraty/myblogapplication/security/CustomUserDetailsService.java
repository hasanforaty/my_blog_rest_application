package com.hasan.foraty.myblogapplication.security;

import com.hasan.foraty.myblogapplication.entity.Users;
import com.hasan.foraty.myblogapplication.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users users =userRepository.findByUsernameOrEmail(username,username).orElseThrow(()-> new UsernameNotFoundException("User not found with username or email : "+username));
    List<GrantedAuthority> authorities = users.getRoles().stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(
        Collectors.toList());
    return User.builder()
        .username(users.getUsername())
        .password(users.getPassword())
        .authorities(authorities)
        .build();
  }
}
