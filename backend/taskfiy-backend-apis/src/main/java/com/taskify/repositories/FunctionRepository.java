package com.taskify.repositories;

import com.taskify.models.FunctionModel;
import com.taskify.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionModel, Integer> {

    List<FunctionModel> findByTaskModel(TaskModel taskModel);

    List<FunctionModel> findByIsFunctionDone(boolean isFunctionDone);

}
