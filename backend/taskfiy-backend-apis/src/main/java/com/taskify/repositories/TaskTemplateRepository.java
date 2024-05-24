package com.taskify.repositories;

import com.taskify.models.templates.TaskTemplateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTemplateRepository extends JpaRepository<TaskTemplateModel, Integer> {

}
