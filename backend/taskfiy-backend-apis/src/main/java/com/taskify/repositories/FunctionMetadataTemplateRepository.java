package com.taskify.repositories;

import com.taskify.models.templates.FunctionMetadataTemplateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionMetadataTemplateRepository extends JpaRepository<FunctionMetadataTemplateModel, Integer> {

    List<FunctionMetadataTemplateModel> findByFunctionTemplateModelId(Integer functionTemplateModelId);

}
