package com.work.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.Usercount;

public interface CountRepository extends JpaRepository<Usercount, Integer> {

}
