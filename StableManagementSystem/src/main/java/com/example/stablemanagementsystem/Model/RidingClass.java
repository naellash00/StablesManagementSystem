package com.example.stablemanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class RidingClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Class Date Cannot Be Empty")
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String day;
    @PrePersist
    @PreUpdate
    public void setDay() {
        if (date != null) {
            this.day = date.getDayOfWeek().toString();
        }
    }
    @Column(columnDefinition = "int unique")
    private Integer traineeid;
    @Column(columnDefinition = "int unique")
    private Integer coachid;
    @Column(columnDefinition = "int unique")
    private Integer horseid;

    private Integer numberoftrainees=0;
    private Integer numberofcoaches=0;
    private Integer numberOfHorses=0;
}
