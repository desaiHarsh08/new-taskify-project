package com.taskify.services;

import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.dtos.MetaFieldDto;
import com.taskify.models.FunctionMetadataModel;
import com.taskify.models.MetaFieldModel;

import java.util.List;

public interface MetaFieldServices {

    public List<MetaFieldModel> createMetaField(FunctionMetadataTemplateDto functionMetadataTemplateDto, FunctionMetadataModel functionMetadataModel);

    public MetaFieldModel createDefaultMetaField(Integer functionMetadataModelId, MetaFieldDto metaFieldDto);

    public List<MetaFieldModel> fetchAllMetaField();

    public MetaFieldModel fetchByMetaFieldId(Integer metaFieldId);

    public List<MetaFieldModel> fetchByFunctionMetadataId(Integer functionMetadataId);

    public MetaFieldModel updateMetaField(MetaFieldModel metaFieldModel, Integer metaFieldId);

    public Integer deleteMetaField(Integer metaFieldId);
    
}
