package com.example.projecttracker.Model;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Project {

       @NotEmpty(message = "ID cant be empty")
       @Size(min = 3 ,message = "ID should Length more than 2 ")
       private String ID ;

       @NotEmpty(message = "title cant be empty")
       @Size(min = 9 ,message = "title Length more than 8 ")
       private String title ;

       @NotEmpty(message = "description cant be empty")
       @Size(min = 16 ,message = "description Length more than 15 ")
       private String description ;

       @NotEmpty(message = "status cant be empty")
       @Pattern(regexp = "not started|in progress|completed", message = "Status must be 'Not Started', 'In Progress', or 'Completed'")
       private String status;

       @NotEmpty(message = "companyName cant be empty")
       @Size(min = 7 ,message = "companyName should Length more than 6 ")
       private String companyName;





}

