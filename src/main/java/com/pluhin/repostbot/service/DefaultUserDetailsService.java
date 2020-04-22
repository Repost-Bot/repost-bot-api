package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.exception.UserIsNotActiveException;
import com.pluhin.repostbot.model.user.DefaultUserDetails;
import com.pluhin.repostbot.repository.UserRepository;
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
    UserEntity entity = userRepository.findFirstByUsername(username);
    if (entity == null) {
      throw new UsernameNotFoundException(username + " was not found");
    }

    if (entity.getPassword() == null) {
      throw new UserIsNotActiveException(username + " is not active");
    }

    return new DefaultUserDetails(
        entity.getUsername(),
        entity.getPassword(),
        entity.getRole(),
        entity.getDeleted(),
        entity.getPassword() != null
    );
  }
}
