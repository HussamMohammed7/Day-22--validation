package com.example.projecttracker.Controller;


import com.example.projecttracker.Api.ApiResponse;
import com.example.projecttracker.Model.Project;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/project/")

public class ProjectController {

    ArrayList<Project> projects = new ArrayList<Project>();


    @GetMapping("/get")
    public ArrayList<Project> getProjects() {
        return projects;
    }





    @PostMapping("/add")
    public ResponseEntity addProject(@Valid @RequestBody Project project, Errors error) {


        if (error.hasErrors()) {
            String message = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        projects.add(project);

        return ResponseEntity.status(HttpStatus.OK).body
                (
                        new ApiResponse("Project added successfully", "202")
                );
    }


    @PutMapping("/update-id/{id}")
    public ResponseEntity updateIdProject(@PathVariable String id,@Valid @RequestBody Project project , Errors error) {


        Project existingProject = null;
        int index = -1;

        if (error.hasErrors()) {
            String message = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getID().equals(id)) {
                existingProject = projects.get(i);
                index = i;
                break;
            }
        }
        if (index == -1) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Project not found", "400")
            );
        }

        projects.set(index, existingProject);
        return ResponseEntity.status(HttpStatus.OK).body
                (
                        new ApiResponse("Project updated", "202")
                );
    }

    @DeleteMapping ("/delete-id/{id}")
    public ResponseEntity deleteProjectID(@PathVariable String id) {

        for (Project project : projects) {
            if (id.equals(project.getID())) {
                projects.remove(project);
                return ResponseEntity.status(HttpStatus.OK).body(
                 new ApiResponse("Project deleted", "202")
                        );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("project not found", "404")
        );


    }
    @GetMapping("/search-title/{title}")
    public ResponseEntity searchTitleProject(@PathVariable String title ) {

        for (Project project : projects) {
            if (project.getTitle().equals(title)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                 project
                );

            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("project not found", "404")
        );

    }

    @GetMapping("/search-company-name/{companyName}")
    public ResponseEntity searchProject(@PathVariable String companyName ) {

            ArrayList<Project> projectsNew = new ArrayList<>();
        for (Project project : projects) {
            if (project.getCompanyName().equals(companyName)) {
                projectsNew.add(project);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(projectsNew);


    }

    @GetMapping("/update-id-status/{id}")
    public ResponseEntity updateStatusId(@PathVariable String id  ) {

        int index = -1;

        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getID().equals(id)) {
                index = i;
                break;
            }
        }

        if(index == -1){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("project id not found", "404")
           );
        }

        if(projects.get(index).getStatus().equals("not started")){
            projects.get(index).setStatus("in progress");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("project updated", "202")
            );
        } else if (projects.get(index).getStatus().equals("in progress")) {
            projects.get(index).setStatus("completed");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("project updated", "202")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse ("project not updated, check your status typing", "404")
        );
    }


}
