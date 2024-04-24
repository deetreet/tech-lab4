package com.example.Application.Repositories;

import com.example.Application.Models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    public Owner findOwnerByName(String name);
}
