package com.taskify.services;

import com.taskify.dtos.FunctionMetadataDto;
import com.taskify.models.FunctionMetadataModel;

import java.sql.Date;
import java.util.List;

public interface FunctionMetadataServices {

    public FunctionMetadataDto createFunctionMetadata(FunctionMetadataDto functionMetadataDto);

    public FunctionMetadataDto createDefaultFunctionMetadata(FunctionMetadataDto functionMetadataDto);

    public List<FunctionMetadataDto> fetchAllFunctionMetadata();

    public List<FunctionMetadataDto> fetchAllFunctionMetadataDone();

    public FunctionMetadataDto fetchByFunctionMetadataId(Integer functionMetadataId);

    public List<FunctionMetadataDto> fetchByFunctionId(Integer functionId);

    public void forwardMetadata(Integer assignedUserId, Integer functionMetadataModelId);

    public FunctionMetadataDto updateFunctionMetadata(FunctionMetadataModel functionMetadataModel);

    public void markFunctionMetadataDone(Integer functionMetadataId, Integer finishedByUserId, Date finishedDate);

    public Integer deleteFunctionMetadata(Integer functionMetadataId);
    
}
