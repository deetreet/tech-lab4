package com.example.Application.Repositories;

import com.example.Application.Models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
    public List<Cat> findCatsByName(String name);
    public List<Cat> findCatsByColorId(Integer colorId);
}
