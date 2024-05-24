package com.taskify.repositories;

import com.taskify.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {

    List<TaskModel> findByIsTaskCompleted(boolean isTaskCompleted);

}
