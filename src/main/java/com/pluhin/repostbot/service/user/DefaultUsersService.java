package com.pluhin.repostbot.service.user;

import com.pluhin.repostbot.entity.PrivilegeEntity;
import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.model.user.CreateUserDTO;
import com.pluhin.repostbot.model.user.CurrentUserDTO;
import com.pluhin.repostbot.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUsersService implements UsersService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public DefaultUsersService(UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void createUser(CreateUserDTO createUserDTO) {
    UserEntity userEntity = new UserEntity();
    String password = RandomStringUtils.random(8);
    userEntity.setUsername(createUserDTO.getUsername());
    userEntity.setPassword(passwordEncoder.encode(password));
    userEntity.setFullName(createUserDTO.getFullName());

    userRepository.save(userEntity);
  }

  @Override
  public CurrentUserDTO getCurrentUser() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(Authentication::getPrincipal)
        .map(x -> (User) x)
        .map(User::getUsername)
        .map(userRepository::getFirstByUsername)
        .map(x -> new CurrentUserDTO(
            x.getUsername(),
            castPrivileges(x)
        ))
        .orElse(null);
  }

  private List<String> castPrivileges(UserEntity userEntity) {
    return userEntity
        .getRoleId()
        .getPrivileges()
        .stream()
        .map(PrivilegeEntity::getName)
        .collect(Collectors.toList());
  }
}
