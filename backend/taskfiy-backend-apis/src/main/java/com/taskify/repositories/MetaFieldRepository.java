package com.taskify.repositories;

import com.taskify.models.FunctionMetadataModel;
import com.taskify.models.MetaFieldModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaFieldRepository extends JpaRepository<MetaFieldModel, Integer> {

    List<MetaFieldModel> findByFunctionMetadataModelId(Integer functionMetadataModelId);
}
