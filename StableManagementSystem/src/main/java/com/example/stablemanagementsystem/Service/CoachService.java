package com.example.stablemanagementsystem.Service;

import com.example.stablemanagementsystem.ApiResponse.ApiException;
import com.example.stablemanagementsystem.Model.Coach;
import com.example.stablemanagementsystem.Repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    public CoachService(CoachRepository coachRepository){
        this.coachRepository = coachRepository;
    }

    public List<Coach> getAllCoaches(){
        return coachRepository.findAll();
    }

    public void addCoach(Coach coach){
        coachRepository.save(coach);
    }

    public void updateCoach(Integer id, Coach coach){
        Coach oldCoach = coachRepository.findCoachById(id);
        if(oldCoach==null){
            throw new ApiException("Coach ID Not Found");
        }
        oldCoach.setAge(coach.getAge());
        oldCoach.setEmail(coach.getEmail());
        oldCoach.setFullName(coach.getFullName());
        oldCoach.setPhone(coach.getPhone());
        oldCoach.setSalary(coach.getSalary());
        coachRepository.save(oldCoach);
    }

    public void deleteCoach(Integer id){
        Coach coach = coachRepository.findCoachById(id);
        if(coach==null){
            throw new ApiException("Coach ID Not Found");
        }
        coachRepository.delete(coach);
    }


}
