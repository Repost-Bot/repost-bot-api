package com.pluhin.repostbot.service.user;

import com.pluhin.repostbot.entity.PrivilegeEntity;
import com.pluhin.repostbot.repository.PrivilegesRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultPrivilegesService implements PrivilegesService {

  private final PrivilegesRepository privilegesRepository;

  public DefaultPrivilegesService(PrivilegesRepository privilegesRepository) {
    this.privilegesRepository = privilegesRepository;
  }

  @Override
  public List<PrivilegeEntity> getAll() {
    return privilegesRepository.findAll();
  }
}
