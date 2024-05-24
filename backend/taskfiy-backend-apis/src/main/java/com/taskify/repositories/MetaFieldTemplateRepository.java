package com.taskify.repositories;

import com.taskify.models.templates.MetaFieldTemplateModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetaFieldTemplateRepository extends JpaRepository<MetaFieldTemplateModel, Integer> {

    List<MetaFieldTemplateModel> findByFunctionMetadataTemplateModelId(Integer functionMetadataTemplateModelId);


}
