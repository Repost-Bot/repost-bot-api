package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
