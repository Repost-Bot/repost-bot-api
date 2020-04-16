package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.model.user.UserDTO;
import com.pluhin.repostbot.repository.UserRepository;
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
    userRepository.removeByUsername(username);
  }

  @Override
  public List<UserDTO> findUsers(int page, int size, String sortField, Direction sortDirection) {
    Pageable pageable = createPageRequest(page, size, sortField, sortDirection);

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
        entity.getRegisteredAt(),
        entity.getConfirmedAt()
    );
  }

  private Pageable createPageRequest(int page, int size, String sortField, Direction sortDirection) {
    Pageable pageable = null;
    if (size == 0) {
      size = 10;
    }

    if (sortDirection == null || sortField == null) {
      pageable = PageRequest.of(page, size);
    } else {
      pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
    }

    return pageable;
  }
}
