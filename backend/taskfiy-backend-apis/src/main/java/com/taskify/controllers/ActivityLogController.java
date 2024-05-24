package com.taskify.controllers;

import com.taskify.models.ActivityLogModel;
import com.taskify.services.ActivityLogServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogServices activityLogServices;

    @GetMapping("")
    public ApiResponse getActivityLogs() {
        return new ApiResponse(
                200,
                HttpStatus.OK,
                "TODAY'S LOG",
                this.activityLogServices.getTodaysActivities(),
                true
        );
    }

}
