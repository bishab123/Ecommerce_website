package com.work.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.UserInteraction;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Integer> {

}
