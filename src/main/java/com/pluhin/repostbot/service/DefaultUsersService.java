package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.model.user.UserDTO;
import com.pluhin.repostbot.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class DefaultUsersService implements UsersService {

  private final UserRepository userRepository;

  public DefaultUsersService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void remove(String username) {
    UserEntity userEntity = userRepository.findFirstByUsername(username);
    userEntity.setDeleted(true);
    userEntity.setDeletedAt(LocalDateTime.now());
    userRepository.save(userEntity);
  }

  @Override
  public List<UserDTO> findUsers(int page, int size, String sortField, Direction sortDirection) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

    return userRepository.findAll(pageable)
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  private UserDTO toDTO(UserEntity entity) {
    return new UserDTO(
        entity.getId(),
        entity.getUsername(),
        entity.getFullName(),
        entity.getDeleted(),
        entity.getRegisteredAt(),
        entity.getConfirmedAt(),
        entity.getDeletedAt()
    );
  }
}
