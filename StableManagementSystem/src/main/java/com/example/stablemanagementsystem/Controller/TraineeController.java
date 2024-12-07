package com.example.stablemanagementsystem.Controller;

import com.example.stablemanagementsystem.ApiResponse.ApiResponse;
import com.example.stablemanagementsystem.Model.Trainee;
import com.example.stablemanagementsystem.Repository.TraineeRepository;
import com.example.stablemanagementsystem.Service.TraineeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping("/get")
    public ResponseEntity getAllTrainees() {
        List<Trainee> trainees = traineeService.getAllTrainees();
        return ResponseEntity.status(200).body(trainees);
    }

    @PostMapping("/add")
    public ResponseEntity addTrainee(@RequestBody @Valid Trainee trainee, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        traineeService.addTrainee(trainee);
        return ResponseEntity.status(200).body(new ApiResponse("Trainee Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTrainee(@PathVariable Integer id, @RequestBody @Valid Trainee trainee, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        traineeService.updateTrainee(id, trainee);
        return ResponseEntity.status(200).body(new ApiResponse("Trainee Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTrainee(@PathVariable Integer id) {
        traineeService.deleteTrainee(id);
        return ResponseEntity.status(200).body(new ApiResponse("Trainee Updated Successfully"));
    }

    @PostMapping("/subscribe/{traineeid}/{type}")
    public ResponseEntity subscribe(@PathVariable Integer traineeid, @PathVariable String type) {
        traineeService.subscribe(traineeid, type);
        return ResponseEntity.status(200).body(new ApiResponse("Subscribed Successfully"));
    }

    @PostMapping("/book/class/{traineeid}/{date}")
    public ResponseEntity bookClass(@PathVariable Integer traineeid, @PathVariable LocalDateTime date) {
        traineeService.bookClass(traineeid, date);
        return ResponseEntity.status(200).body(new ApiResponse("Class Is Booked Successfully"));
    }
}
