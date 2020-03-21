package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegesRepository extends JpaRepository<PrivilegeEntity, Long> {

}
