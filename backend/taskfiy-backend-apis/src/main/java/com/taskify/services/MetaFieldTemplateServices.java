package com.taskify.services;

import com.taskify.models.templates.MetaFieldTemplateModel;

import java.util.List;

public interface MetaFieldTemplateServices {

    public MetaFieldTemplateModel createMetaField(MetaFieldTemplateModel metaFieldTemplateModel);

    public List<MetaFieldTemplateModel> fetchAllMetaFields();

    public MetaFieldTemplateModel fetchById(Integer metaFieldId);

    public List<MetaFieldTemplateModel> fetchByFunctionMetadataTemplateId(Integer functionMetadataTemplateId);

    public MetaFieldTemplateModel updateMetaField(MetaFieldTemplateModel metaFieldTemplateModel, Integer metaFieldId);
    public Integer deleteMetaField(Integer metaFieldId);

}
