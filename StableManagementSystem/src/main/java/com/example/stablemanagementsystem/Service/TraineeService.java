package com.example.stablemanagementsystem.Service;

import com.example.stablemanagementsystem.ApiResponse.ApiException;
import com.example.stablemanagementsystem.ApiResponse.ApiResponse;
import com.example.stablemanagementsystem.Model.RidingClass;
import com.example.stablemanagementsystem.Model.Subscription;
import com.example.stablemanagementsystem.Model.Trainee;
import com.example.stablemanagementsystem.Repository.RidingClassRepository;
import com.example.stablemanagementsystem.Repository.SubscriptionRepository;
import com.example.stablemanagementsystem.Repository.TraineeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final SubscriptionService subscriptionService;
    private final RidingClassService ridingClassService;
    private final RidingClassRepository ridingClassRepository;

    public TraineeService(TraineeRepository traineeRepository, SubscriptionRepository subscriptionRepository, SubscriptionService subscriptionService, RidingClassService ridingClassService, RidingClassRepository ridingClassRepository) {
        this.traineeRepository = traineeRepository;

        this.subscriptionService = subscriptionService;
        this.ridingClassService = ridingClassService;
        this.ridingClassRepository = ridingClassRepository;
    }

    public List<Trainee> getAllTrainees() {
        List<Trainee> trainees = traineeRepository.findAll();
        return trainees;
    }

    public void addTrainee(Trainee trainee) {
        traineeRepository.save(trainee);
    }

    public void updateTrainee(Integer id, Trainee trainee) {
        Trainee oldTrainee = traineeRepository.findTraineeById(id);
        if (oldTrainee == null) {
            throw new ApiException("Trainee ID Not Found");
        }
        oldTrainee.setFullName(trainee.getFullName());
        oldTrainee.setAge(trainee.getAge());
        oldTrainee.setPhone(trainee.getPhone());
        oldTrainee.setEmail(trainee.getEmail());
        traineeRepository.save(oldTrainee);
    }

    public void deleteTrainee(Integer id) {
        Trainee trainee = traineeRepository.findTraineeById(id);
        if (trainee == null) {
            throw new ApiException("Trainee ID Not Found");
        }
        traineeRepository.delete(trainee);
    }

    public void subscribe(Integer traineeID, String type) {
        // to subscribe 1.choose the type of subscription and then pay
        for (Trainee trainee : getAllTrainees()) {
            // check the id of trainee who wants to subscribe is
            if (trainee.getId().equals(traineeID)) {
                // check balance
                if (trainee.getBalance() > subscriptionService.getPriceByType(type)) {
                    // pay
                    trainee.setBalance(trainee.getBalance() - subscriptionService.getPriceByType(type));
                    // create the new subscription
                    Subscription subscription = subscriptionService.setSubscriptionDetailsBasedOnType(type);
                    subscription.setTraineeid(trainee.getId());
                    // and add it
                    subscriptionService.addSubscription(subscription);
                    //trainee.setSubscriptionid(subscription.getId());
                    // System.out.println(isSubscribed(trainee.getId()));
                    isSubscribed(trainee.getId());
                } else {
                    throw new ApiException("Balance Not Enough To Subscribe");
                }
            } else {
                throw new ApiException("Trainee ID Not Found");
            }
        }

    }

    public Boolean isSubscribed(Integer traineeID) {
        // check id
        for (Trainee trainee : getAllTrainees()) {
            if (trainee.getId().equals(traineeID)) {
                return true;
            }
        }
        return false;
    }

    public void bookClass(Integer traineeID, LocalDateTime date){
        for(Trainee trainee : getAllTrainees()){
            // check trainee id and subscription
            if(trainee.getId().equals(traineeID) && isSubscribed(traineeID)){
                //****check if trainee already booked this class before***
                // check if date is available
                if(ridingClassService.isAvailableDate(date)){
                    // then make new riding class object
                    RidingClass ridingClass1 = new RidingClass();
                    ridingClass1.setTraineeid(trainee.getId());
                    ridingClass1.setDate(date);

                    Integer assignedCoachID = ridingClassService.assignedCoach();
                    Integer assignedHorseID = ridingClassService.assignedHorse();

                    if(assignedCoachID != null){
                        ridingClass1.setCoachid(assignedCoachID);
                        ridingClass1.setNumberofcoaches(ridingClass1.getNumberofcoaches() + 1);

                    }
                     else {
                         throw new ApiException("No Available Coach For This Class");
                    }
                     if(assignedHorseID != null){
                         ridingClass1.setHorseid(assignedHorseID);
                         ridingClass1.setNumberOfHorses(ridingClass1.getNumberOfHorses() + 1);
                     }
                     else {
                         throw new ApiException("No Available Coach For This Class");
                     }
                     // increase number of trainees
                    ridingClass1.setNumberoftrainees(ridingClass1.getNumberoftrainees() + 1);
                    ridingClassRepository.save(ridingClass1);
                    //decrement this trainee number of classes
                    trainee.setNumberOfRemainingClasses(trainee.getNumberOfRemainingClasses()-1);

                }
                else {
                    throw new ApiException("Incorrect Booking. Date Is Unavailable");
                }

            }
        }

    }

}
