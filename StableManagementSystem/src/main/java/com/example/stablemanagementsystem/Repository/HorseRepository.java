package com.example.stablemanagementsystem.Repository;

import com.example.stablemanagementsystem.Model.Horse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseRepository extends JpaRepository<Horse, Integer> {
    Horse findHorseById(Integer id);
}
