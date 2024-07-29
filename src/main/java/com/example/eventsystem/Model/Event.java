package com.example.eventsystem.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor



public class Event {
    @NotEmpty(message = "ID cant be empty")
    @Size(min = 3 ,message = "ID should Length more than 2 ")
    private String id;

    @NotEmpty(message = "description cant be empty")
    @Size(min = 16 ,message = "description Length more than 15 ")
    private String description;

    @NotNull(message = "capacity cant be null")
    @Positive (message = "capacity should be a number ")
    @Min(value = 26,message = "capacity must be more than 25 ")
    private int capacity;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;


}
