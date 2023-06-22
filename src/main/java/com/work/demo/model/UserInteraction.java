package com.work.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class UserInteraction {
	
	
	@Id
	@GeneratedValue
    private int id;
    
    
    @ManyToOne
    private UserInfo userinfos;
    
    @ManyToOne
    private Product product;
    
    
    
    private int gainedpoints;
    
    private String description;
    
    private int gainedpointsbyshop;
}
