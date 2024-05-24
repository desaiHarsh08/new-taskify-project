package com.taskify.services.impl;

import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.templates.MetaFieldTemplateModel;
import com.taskify.repositories.MetaFieldTemplateRepository;
import com.taskify.services.MetaFieldTemplateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaFieldTemplateServicesImpl implements MetaFieldTemplateServices {

    @Autowired
    private MetaFieldTemplateRepository metaFieldTemplateRepository;


    @Override
    public MetaFieldTemplateModel createMetaField(MetaFieldTemplateModel metaFieldTemplateModel) {
        System.out.println("in meta field service: -" + metaFieldTemplateModel);
        return this.metaFieldTemplateRepository.save(metaFieldTemplateModel);
    }

    @Override
    public List<MetaFieldTemplateModel> fetchAllMetaFields() {
        return this.metaFieldTemplateRepository.findAll();
    }

    @Override
    public List<MetaFieldTemplateModel> fetchByFunctionMetadataTemplateId(Integer functionMetadataTemplateId) {
        return this.metaFieldTemplateRepository.findByFunctionMetadataTemplateModelId(functionMetadataTemplateId);
    }

    @Override
    public MetaFieldTemplateModel fetchById(Integer metaFieldId) {
        return this.metaFieldTemplateRepository.findById(metaFieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Metafield", "id", metaFieldId));
    }

    @Override
    public MetaFieldTemplateModel updateMetaField(MetaFieldTemplateModel metaFieldTemplateModel, Integer metaFieldId) {
        MetaFieldTemplateModel existingMetaField = this.fetchById(metaFieldId);
        existingMetaField.setFieldName(metaFieldTemplateModel.getFieldName());
        existingMetaField.setFieldValue(metaFieldTemplateModel.getFieldValue());
        return this.metaFieldTemplateRepository.save(existingMetaField);
    }

    @Override
    public Integer deleteMetaField(Integer metaFieldId) {
        if(this.metaFieldTemplateRepository.findById(metaFieldId) == null) {
            return -1;
        }
        this.metaFieldTemplateRepository.deleteById(metaFieldId);
        return metaFieldId;
    }
}
