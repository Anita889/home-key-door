package com.example.homekeydoor.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@Table(name = "home_owners")
public class HomeOwner{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
