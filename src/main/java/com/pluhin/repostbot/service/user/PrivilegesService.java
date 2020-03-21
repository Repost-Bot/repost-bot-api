package com.pluhin.repostbot.service.user;

import com.pluhin.repostbot.entity.PrivilegeEntity;
import java.util.List;

public interface PrivilegesService {

  List<PrivilegeEntity> getAll();
}
