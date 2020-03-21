package com.pluhin.repostbot.service.user;

import static java.util.stream.Collectors.toList;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.repository.UserRepository;
import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public DefaultUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.getFirstByUsername(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException(username + " was not found");
    }

    return new User(
        userEntity.getUsername(),
        userEntity.getPassword(),
        getAuthorities(userEntity)
    );
  }

  private Collection<GrantedAuthority> getAuthorities(UserEntity userEntity) {
    return userEntity
        .getRoleId()
        .getPrivileges()
        .stream()
        .map(Objects::toString)
        .map(SimpleGrantedAuthority::new)
        .collect(toList());
  }
}
