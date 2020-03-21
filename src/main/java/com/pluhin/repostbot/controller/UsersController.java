package com.pluhin.repostbot.controller;

import static com.pluhin.repostbot.model.user.PrivilegeConstants.CREATE_USERS;

import com.pluhin.repostbot.model.user.CreateUserDTO;
import com.pluhin.repostbot.model.user.CurrentUserDTO;
import com.pluhin.repostbot.service.user.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final UsersService usersService;

  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @Secured(CREATE_USERS)
  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO) {
    usersService.createUser(createUserDTO);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/current")
  public ResponseEntity<CurrentUserDTO> getCurrentUser() {
    return ResponseEntity.ok(usersService.getCurrentUser());
  }
}
