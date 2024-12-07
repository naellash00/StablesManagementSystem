package com.example.stablemanagementsystem.Service;

import com.example.stablemanagementsystem.Model.Coach;
import com.example.stablemanagementsystem.Model.Horse;
import com.example.stablemanagementsystem.Model.RidingClass;
import com.example.stablemanagementsystem.Model.Trainee;
import com.example.stablemanagementsystem.Repository.RidingClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RidingClassService {
    private final RidingClassRepository ridingClassRepository;
    private final HorseService horseService;
    private final CoachService coachService;

    public RidingClassService(RidingClassRepository ridingClassRepository, HorseService horseService, CoachService coachService) {
        this.ridingClassRepository = ridingClassRepository;
        this.horseService = horseService;
        this.coachService = coachService;
    }

    List<Trainee> classTrainees;
    List<Coach> classCoaches;
    List<Horse> classHorses;

    public List<RidingClass> getClassesInformation() {
        return ridingClassRepository.findAll();
    }


    public Boolean isAvailableDate(LocalDateTime date) {
        if (ridingClassRepository.getNumberOfTraineesByClassDate(date) == null ||
                ridingClassRepository.getNumberOfTraineesByClassDate(date) < 6) {
            // class is not full
//            for (RidingClass ridingClass : ridingClassRepository.findAll()) {
//                if (ridingClass.getDate().equals(date)) {
//                    return false;
//                }
//            }
            // Check if a class already exists at the given date
            if (ridingClassRepository.existsByDate(date)) {
                return false;
            }

        }
        return true;
    }

    public  Integer assignedCoach() {
        for (Coach coach : coachService.getAllCoaches()) {
            // check is this coach status is available
            if (coach.getStatus().equalsIgnoreCase("available")) {
                coach.setStatus("assigned");
                return coach.getId();
            }
        }
        return null; // no available coach
    }

    public Integer assignedHorse() {
        for (Horse horse : horseService.getAllHorses()) {
            if (horse.getStatus().equalsIgnoreCase("available")) {
                horse.setStatus("assigned");
                return horse.getId();
            }
        }
        return null; // no available horse
    }
}
