package com.pluhin.repostbot.service.user;

import com.pluhin.repostbot.model.user.CreateUserDTO;
import com.pluhin.repostbot.model.user.CurrentUserDTO;

public interface UsersService {

  void createUser(CreateUserDTO createUserDTO);

  CurrentUserDTO getCurrentUser();
}
