package com.taskify.services.impl;

import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.templates.FunctionMetadataTemplateModel;
import com.taskify.models.templates.FunctionTemplateModel;
import com.taskify.models.templates.MetaFieldTemplateModel;
import com.taskify.repositories.FunctionMetadataTemplateRepository;
import com.taskify.services.FunctionMetadataTemplateServices;
import com.taskify.services.MetaFieldTemplateServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionMetadataTemplateServicesImpl implements FunctionMetadataTemplateServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FunctionMetadataTemplateRepository functionMetadataTemplateRepository;

    @Autowired
    private MetaFieldTemplateServices metaFieldTemplateServices;


    @Override
    public FunctionMetadataTemplateDto createFunctionMetadataTemplate(FunctionMetadataTemplateDto functionMetadataTemplateDto) {
        // Create the functionMetadataTemplate
        System.out.println("creating func metadata -n func metadata services");
        FunctionMetadataTemplateModel createdMetadataTemplate = this.functionMetadataTemplateRepository.save(this.modelMapper.map(functionMetadataTemplateDto, FunctionMetadataTemplateModel.class));

        functionMetadataTemplateDto.setId(createdMetadataTemplate.getId());
        functionMetadataTemplateDto.setFunctionTemplateModelId(createdMetadataTemplate.getFunctionTemplateModel().getId());

        System.out.println("created function metadata template:"+ createdMetadataTemplate);

        // Create the metaFieldsTemplate
        for (int i = 0; i < functionMetadataTemplateDto.getMetaFieldTemplateModelList().size(); i++) {
            System.out.println("creating metafields in loop");
            MetaFieldTemplateModel m = new MetaFieldTemplateModel();

            m.setFieldName(functionMetadataTemplateDto.getMetaFieldTemplateModelList().get(i).getFieldName());
            m.setFieldValue(functionMetadataTemplateDto.getMetaFieldTemplateModelList().get(i).getFieldValue());

            m.setFunctionMetadataTemplateModel(createdMetadataTemplate);

            MetaFieldTemplateModel createdMetaFieldTemplateModel = this.metaFieldTemplateServices.createMetaField(m);
            System.out.println("in loop: -" +createdMetaFieldTemplateModel);

        }

        return functionMetadataTemplateDto;
    }

    @Override
    public List<FunctionMetadataTemplateDto> fetchAllFunctionMetadataTemplates() {
        List<FunctionMetadataTemplateModel> functionMetadataTemplateModelList = this.functionMetadataTemplateRepository.findAll();

        List<FunctionMetadataTemplateDto> functionMetadataTemplateDtoList = new ArrayList<>();

        for (FunctionMetadataTemplateModel functionMetadataTemplateModel: functionMetadataTemplateModelList) {
            FunctionMetadataTemplateDto functionMetadataTemplateDto = this.modelMapper.map(functionMetadataTemplateModel, FunctionMetadataTemplateDto.class);
            functionMetadataTemplateDto.setMetaFieldTemplateModelList(this.metaFieldTemplateServices.fetchByFunctionMetadataTemplateId(functionMetadataTemplateModel.getId()));

            functionMetadataTemplateDto.setFunctionTemplateModelId(functionMetadataTemplateModel.getFunctionTemplateModel().getId());

            functionMetadataTemplateDtoList.add(functionMetadataTemplateDto);
        }


        return functionMetadataTemplateDtoList;
    }

    @Override
    public FunctionMetadataTemplateDto fetchById(Integer functionMetadataTemplateId) {
         FunctionMetadataTemplateModel functionMetadataTemplateModel = this.functionMetadataTemplateRepository.findById(functionMetadataTemplateId)
                .orElseThrow(() -> new ResourceNotFoundException("FunctionMetadataTemplate", "id", functionMetadataTemplateId));

        FunctionMetadataTemplateDto functionMetadataTemplateDto= this.modelMapper.map(functionMetadataTemplateModel, FunctionMetadataTemplateDto.class);
        functionMetadataTemplateDto.setFunctionTemplateModelId(functionMetadataTemplateModel.getFunctionTemplateModel().getId());
        functionMetadataTemplateDto.setMetaFieldTemplateModelList(this.metaFieldTemplateServices.fetchByFunctionMetadataTemplateId(functionMetadataTemplateDto.getId()));

        return functionMetadataTemplateDto;
    }

    public List<FunctionMetadataTemplateDto> fetchByFunctionTemplateId(Integer functionTemplateModelId) {
        List<FunctionMetadataTemplateModel> functionMetadataTemplateModelList = this.functionMetadataTemplateRepository.findByFunctionTemplateModelId(functionTemplateModelId);

        List<FunctionMetadataTemplateDto> functionMetadataTemplateDtoList = new ArrayList<>();

        for (FunctionMetadataTemplateModel functionMetadataTemplateModel: functionMetadataTemplateModelList) {
            FunctionMetadataTemplateDto functionMetadataTemplateDto = this.modelMapper.map(functionMetadataTemplateModel, FunctionMetadataTemplateDto.class);

            functionMetadataTemplateDto.setFunctionTemplateModelId(functionMetadataTemplateModel.getFunctionTemplateModel().getId());

            functionMetadataTemplateDto.setMetaFieldTemplateModelList(this.metaFieldTemplateServices.fetchByFunctionMetadataTemplateId(functionMetadataTemplateDto.getId()));

            functionMetadataTemplateDtoList.add(functionMetadataTemplateDto);
        }

        return functionMetadataTemplateDtoList;
    }

    @Override
    public FunctionMetadataTemplateDto updateFunctionMetadata(FunctionMetadataTemplateModel functionMetadataTemplateModel, Integer functionMetadataId) {
        FunctionMetadataTemplateModel existingFunctionMetadataTemplateModel = this.modelMapper.map(this.fetchById(functionMetadataId), FunctionMetadataTemplateModel.class);
        existingFunctionMetadataTemplateModel.setMetadataTitle(functionMetadataTemplateModel.getMetadataTitle());
        existingFunctionMetadataTemplateModel.setMetadataDescription(functionMetadataTemplateModel.getMetadataDescription());
        existingFunctionMetadataTemplateModel.setFunctionTemplateModel(functionMetadataTemplateModel.getFunctionTemplateModel());


        FunctionMetadataTemplateDto functionMetadataTemplateDto = this.modelMapper.map(this.functionMetadataTemplateRepository.save(existingFunctionMetadataTemplateModel), FunctionMetadataTemplateDto.class);
        functionMetadataTemplateDto.setFunctionTemplateModelId(functionMetadataTemplateModel.getFunctionTemplateModel().getId());

        return functionMetadataTemplateDto;
    }


    @Override
    public Integer deleteFunctionMetadata(Integer functionMetadataId) {
        if(this.functionMetadataTemplateRepository.findById(functionMetadataId).isEmpty()) {
            return  -1;
        }

        // Delete the meta fields
        List<MetaFieldTemplateModel> metaFieldTemplateModelList = this.metaFieldTemplateServices.fetchByFunctionMetadataTemplateId(functionMetadataId);
        for (MetaFieldTemplateModel metaFieldTemplateModel: metaFieldTemplateModelList) {
            this.metaFieldTemplateServices.deleteMetaField(metaFieldTemplateModel.getId());
        }

        // Delete the function metadata
        this.functionMetadataTemplateRepository.deleteById(functionMetadataId);
        return functionMetadataId;
    }
}
