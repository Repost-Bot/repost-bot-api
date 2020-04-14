package com.pluhin.repostbot.controller;

import com.pluhin.util.registration.model.ConfirmationEntity;
import com.pluhin.util.registration.model.RegistrationRequest;
import com.pluhin.util.registration.service.ConfirmationService;
import com.pluhin.util.registration.service.RegistrationService;
import com.pluhin.util.registration.service.ValidationConfirmationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  public UsersController(RegistrationService registrationService,
      ConfirmationService confirmationService,
      ValidationConfirmationService validationConfirmationService) {
    this.registrationService = registrationService;
    this.confirmationService = confirmationService;
    this.validationConfirmationService = validationConfirmationService;
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
}
