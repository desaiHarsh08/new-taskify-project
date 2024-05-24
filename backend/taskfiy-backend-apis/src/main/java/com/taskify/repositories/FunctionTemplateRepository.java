package com.taskify.repositories;

import com.taskify.models.templates.FunctionTemplateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionTemplateRepository extends JpaRepository<FunctionTemplateModel, Integer> {

    public FunctionTemplateModel findByFunctionTitle(String functionTitle);

    public List<FunctionTemplateModel> findByFunctionDepartment(String functionDepartment);

    public List<FunctionTemplateModel> findByTaskTemplateModelId(Integer taskTemplateModelId);

}
