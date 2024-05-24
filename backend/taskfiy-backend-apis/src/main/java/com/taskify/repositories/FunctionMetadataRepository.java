package com.taskify.repositories;

import com.taskify.models.FunctionMetadataModel;
import com.taskify.models.FunctionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionMetadataRepository extends JpaRepository<FunctionMetadataModel, Integer> {

    List<FunctionMetadataModel> findByIsFunctionMetadataDone(boolean isFunctionMetadataDone);

    List<FunctionMetadataModel> findByFunctionModel(FunctionModel functionModel);
}
