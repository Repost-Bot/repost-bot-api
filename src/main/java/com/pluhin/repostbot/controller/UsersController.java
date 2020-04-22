package com.pluhin.repostbot.controller;

import com.pluhin.repostbot.model.user.UserDTO;
import com.pluhin.repostbot.service.UsersService;
import com.pluhin.util.registration.model.ConfirmationEntity;
import com.pluhin.util.registration.model.RegistrationRequest;
import com.pluhin.util.registration.service.ConfirmationService;
import com.pluhin.util.registration.service.RegistrationService;
import com.pluhin.util.registration.service.ValidationConfirmationService;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final RegistrationService registrationService;
  private final ConfirmationService confirmationService;
  private final ValidationConfirmationService validationConfirmationService;
  private final UsersService usersService;

  public UsersController(RegistrationService registrationService,
      ConfirmationService confirmationService,
      ValidationConfirmationService validationConfirmationService,
      UsersService usersService) {
    this.registrationService = registrationService;
    this.confirmationService = confirmationService;
    this.validationConfirmationService = validationConfirmationService;
    this.usersService = usersService;
  }

  @GetMapping
  public List<UserDTO> getUsersPage(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "id") String sortField,
      @RequestParam(required = false, defaultValue = "ASC") Direction sortDirection
  ) {
    return usersService.findUsers(page, size, sortField, sortDirection);
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<Void> removeUser(@PathVariable String username) {
    usersService.remove(username);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody RegistrationRequest request) {
    registrationService.register(request);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/confirm")
  public ResponseEntity<Void> confirmRegistration(@RequestBody ConfirmationEntity entity) {
    confirmationService.confirm(entity);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/confirm")
  public ResponseEntity<Void> validateConfirmation(@RequestParam String token) {
    Boolean result = validationConfirmationService.isValid(token);
    if (result) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/current")
  public UserDTO currentUser() {
    return usersService.getCurrentUser();
  }
}
