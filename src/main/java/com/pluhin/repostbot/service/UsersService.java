package com.pluhin.repostbot.service;

import com.pluhin.repostbot.model.user.UserDTO;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;

public interface UsersService {

  void remove(String username);

  List<UserDTO> findUsers(int page, int size, String sortField, Direction sortDirection);

  UserDTO getCurrentUser();
}
