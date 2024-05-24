package com.taskify.services.impl;

import com.taskify.dtos.FunctionMetadataDto;
import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.dtos.MetaFieldDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.ActivityLogModel;
import com.taskify.models.FunctionMetadataModel;
import com.taskify.models.MetaFieldModel;
import com.taskify.models.templates.MetaFieldTemplateModel;
import com.taskify.repositories.FunctionMetadataRepository;
import com.taskify.repositories.MetaFieldRepository;
import com.taskify.services.ActivityLogServices;
import com.taskify.services.FunctionMetadataTemplateServices;
import com.taskify.services.MetaFieldServices;
import com.taskify.services.MetaFieldTemplateServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetaFieldServicesImpl implements MetaFieldServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MetaFieldRepository metaFieldRepository;

    @Autowired
    private FunctionMetadataRepository functionMetadataRepository;

    @Autowired
    private MetaFieldTemplateServices metaFieldTemplateServices;

    @Autowired
    private ActivityLogServices activityLogServices;

    @Override
    public List<MetaFieldModel> createMetaField(FunctionMetadataTemplateDto functionMetadataTemplateDto, FunctionMetadataModel functionMetadataModel) {
        // Fetch the MetaFieldTemplateList
        List<MetaFieldTemplateModel> metaFieldTemplateModelList = this.metaFieldTemplateServices.fetchByFunctionMetadataTemplateId(functionMetadataTemplateDto.getId());

        List<MetaFieldModel> metaFieldModelList = new ArrayList<>();

        // Add the meta field
        for (MetaFieldTemplateModel m: metaFieldTemplateModelList) {
            MetaFieldModel metaFieldModel = new MetaFieldModel();
            metaFieldModel.setFunctionMetadataModel(functionMetadataModel);
            metaFieldModel.setFieldName(m.getFieldName());
            metaFieldModel.setFieldValue(m.getFieldValue());

            this.metaFieldRepository.save(metaFieldModel);

            metaFieldModelList.add(metaFieldModel);

        }

        return metaFieldModelList;
    }

    @Override
    public MetaFieldModel createDefaultMetaField(Integer functionMetadataModelId, MetaFieldDto metaFieldDto) {

        FunctionMetadataModel functionMetadataModel = this.functionMetadataRepository.findById(functionMetadataModelId)
                .orElseThrow(()-> new ResourceNotFoundException("FunctionMetadataModel", "id", functionMetadataModelId));

        // Add the meta field
        MetaFieldModel metaFieldModel = new MetaFieldModel();
        metaFieldModel.setFunctionMetadataModel(functionMetadataModel);
        metaFieldModel.setFieldName(metaFieldDto.getFieldName());
        metaFieldModel.setFieldValue(metaFieldDto.getFieldValue());

        this.metaFieldRepository.save(metaFieldModel);


        return metaFieldModel;
    }

    @Override
    public List<MetaFieldModel> fetchAllMetaField() {
        return this.metaFieldRepository.findAll();
    }

    @Override
    public MetaFieldModel fetchByMetaFieldId(Integer metaFieldId) {
        return this.metaFieldRepository.findById(metaFieldId)
                .orElseThrow(() -> new ResourceNotFoundException("MetaField", "id", metaFieldId));
    }

    @Override
    public List<MetaFieldModel> fetchByFunctionMetadataId(Integer functionMetadataId) {
        return this.metaFieldRepository.findByFunctionMetadataModelId(functionMetadataId);
    }

    @Override
    public MetaFieldModel updateMetaField(MetaFieldModel metaFieldModel, Integer metaFieldId) {
        this.fetchByMetaFieldId(metaFieldId);

        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "FUNCTION",
                "UPDATE",
                "Function (" + metaFieldModel.getFunctionMetadataModel().getFunctionModel().getFunctionTitle() + ") got updated by " + metaFieldModel.getFunctionMetadataModel().getFunctionModel().getFunctionAssignedToUser().getName()
        );
        this.activityLogServices.createLog(activityLogModel);

        return this.metaFieldRepository.save(metaFieldModel);
    }

    @Override
    public Integer deleteMetaField(Integer metaFieldId) {
        if(this.metaFieldRepository.findById(metaFieldId).isEmpty()) {
            return -1;
        }
        this.metaFieldRepository.deleteById(metaFieldId);
        return metaFieldId;
    }
}
