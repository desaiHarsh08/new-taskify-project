package com.taskify.services;

import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.models.templates.FunctionMetadataTemplateModel;

import java.util.List;

public interface FunctionMetadataTemplateServices {

    public FunctionMetadataTemplateDto createFunctionMetadataTemplate(FunctionMetadataTemplateDto functionMetadataTemplateDto);

    public List<FunctionMetadataTemplateDto> fetchAllFunctionMetadataTemplates();

    public FunctionMetadataTemplateDto fetchById(Integer functionMetadataTemplateId);

    public List<FunctionMetadataTemplateDto> fetchByFunctionTemplateId(Integer functionTemplateModelId);

    public FunctionMetadataTemplateDto updateFunctionMetadata(FunctionMetadataTemplateModel functionMetadataTemplateModel, Integer functionMetadataId);

    public Integer deleteFunctionMetadata(Integer functionMetadataId);
    
}
