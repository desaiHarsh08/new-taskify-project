package com.taskify.repositories;

import com.taskify.models.ActivityLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLogModel, Integer> {

    @Query("SELECT a FROM ActivityLogModel a WHERE DATE(a.date) = CURRENT_DATE")
    List<ActivityLogModel> findTodaysActivities();

}
