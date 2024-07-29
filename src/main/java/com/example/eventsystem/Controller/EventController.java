package com.example.eventsystem.Controller;


import com.example.eventsystem.Api.ApiResponse;
import com.example.eventsystem.Model.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/events")

public class EventController {


    private ArrayList<Event> events = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity addEvent(@Valid @RequestBody Event event, Errors error) {

        if (error.hasErrors()) {
            String message = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        events.add(event);

        return ResponseEntity.status(HttpStatus.OK).body
                (
                        new ApiResponse("event added successfully", "202")
                );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEvent(@PathVariable String id, @Valid @RequestBody Event event, Errors error) {
        Event existingEvent = null;
        int index = -1;

        if (error.hasErrors()) {
            String message = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(id)) {
                existingEvent = events.get(i);
                index = i;
                break;
            }
        }
        if (index == -1) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Event not found", "400")
            );
        }


        events.set(index, existingEvent);
        return ResponseEntity.status(HttpStatus.OK).body
                (
                        new ApiResponse("Project updated", "202")
                );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEvent(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                events.remove(event);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ApiResponse("Event deleted", "202")
                );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("Event id not found", "404")
        );
    }

    @GetMapping("/get")
    public ArrayList<Event> getEvents() {
        return events;
    }

    @GetMapping("/search/{id}")
    public ResponseEntity searchEventById(@PathVariable String id) {


        for (Event event : events) {
            if (event.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.OK).body
                        (
                                event
                        );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("Event not found", "404")
        );
    }

    @PutMapping("/update/capacity/{id}/{capacity}")
    public ResponseEntity updateEventCapacity(@PathVariable String id, @PathVariable int capacity) {
        if (capacity <= 25) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Capacity Should be a more 25 number", "400")
            )
                    ;
        }
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.setCapacity(capacity);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ApiResponse("Event capacity updated", "202")
                );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("Event id not found", "404")
        );    }


}
