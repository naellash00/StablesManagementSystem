package com.example.stablemanagementsystem.Service;

import com.example.stablemanagementsystem.ApiResponse.ApiException;
import com.example.stablemanagementsystem.Model.Horse;
import com.example.stablemanagementsystem.Repository.HorseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseService {
    private final HorseRepository horseRepository;
    public HorseService(HorseRepository horseRepository) {
        this.horseRepository = horseRepository;
    }

    public List<Horse> getAllHorses(){
        List<Horse> horses = horseRepository.findAll();
        return horses;
    }

    public void addHorse(Horse horse){
        horseRepository.save(horse);
    }

    public void updateHorse(Integer id, Horse horse){
        Horse oldHorse = horseRepository.findHorseById(id);
        if(oldHorse==null){
            throw new ApiException("Horse ID Not Found");
        }
        oldHorse.setName(horse.getName());
        oldHorse.setBreed(horse.getBreed());
        oldHorse.setAge(horse.getAge());
        oldHorse.setRoomnumber(horse.getRoomnumber());
        oldHorse.setStatus(horse.getStatus());
        horseRepository.save(oldHorse);
    }

    public void deleteHorse(Integer id){
        Horse horse = horseRepository.findHorseById(id);
        if(horse==null){
            throw new ApiException("Horse ID Not Found");
        }
        horseRepository.delete(horse);
    }
}
