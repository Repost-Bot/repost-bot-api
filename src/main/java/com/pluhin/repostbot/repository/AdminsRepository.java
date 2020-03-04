package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.AdminsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<AdminsEntity, Long> {

}
