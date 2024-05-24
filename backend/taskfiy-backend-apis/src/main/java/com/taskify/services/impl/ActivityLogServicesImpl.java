package com.taskify.services.impl;

import com.taskify.models.ActivityLogModel;
import com.taskify.repositories.ActivityLogRepository;
import com.taskify.services.ActivityLogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ActivityLogServicesImpl implements ActivityLogServices {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Override
    public List<ActivityLogModel> getTodaysActivities() {
        // Get today's date
        Date today = Date.valueOf(LocalDate.now());

        // Fetch activities for today
        List<ActivityLogModel> activityLogModels = this.activityLogRepository.findTodaysActivities();

        // Log retrieved activities
        System.out.println("\n\nlogs: " + activityLogModels);

        return activityLogModels;
    }

    @Override
    public void createLog(ActivityLogModel activityLogModel) {
        this.activityLogRepository.save(activityLogModel);
    }
}
