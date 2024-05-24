package com.taskify.services.impl;

import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.templates.FunctionMetadataTemplateModel;
import com.taskify.models.templates.FunctionTemplateModel;
import com.taskify.repositories.FunctionTemplateRepository;
import com.taskify.repositories.TaskTemplateRepository;
import com.taskify.services.FunctionMetadataTemplateServices;
import com.taskify.services.FunctionTemplateServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionTemplateServicesImpl implements FunctionTemplateServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Autowired
    private FunctionTemplateRepository functionTemplateRepository;

    @Autowired
    private FunctionMetadataTemplateServices functionMetadataTemplateServices;


    @Override
    public FunctionTemplateDto createFunctionTemplateModel(FunctionTemplateDto functionTemplateDto) {
        FunctionTemplateModel existingFunctionTemplate = this.functionTemplateRepository.findByFunctionTitle(functionTemplateDto.getFunctionTitle());
//        if(existingFunctionTemplate != null) {
//            return null;
//        }
        System.out.println("creating function template in func tmp service");

        // Create the function template
        FunctionTemplateModel createdFunctionTemplate = this.functionTemplateRepository.save(this.modelMapper.map(functionTemplateDto, FunctionTemplateModel.class));

        functionTemplateDto.setId(createdFunctionTemplate.getId());


        // Create the function metadata
        for (FunctionMetadataTemplateDto functionMetadataTemplateDto: functionTemplateDto.getFunctionMetadataTemplateDtos()) {
            functionMetadataTemplateDto.setFunctionTemplateModelId(createdFunctionTemplate.getId());
            System.out.println("creating function metadata ");
            FunctionMetadataTemplateModel createdFunctionMetadataTemplateModel = this.modelMapper.map(this.functionMetadataTemplateServices.createFunctionMetadataTemplate(functionMetadataTemplateDto), FunctionMetadataTemplateModel.class);
            if(createdFunctionMetadataTemplateModel == null) {
                List<FunctionMetadataTemplateDto> functionMetadataTemplateDtos = this.functionMetadataTemplateServices.fetchByFunctionTemplateId(createdFunctionTemplate.getId());
                for (FunctionMetadataTemplateDto f: functionMetadataTemplateDtos) {
                    this.functionMetadataTemplateServices.deleteFunctionMetadata(f.getId());
                }
                this.deleteFunctionTemplate(createdFunctionTemplate.getId());
            }

//            functionTemplateDto.getFunctionMetadataTemplateDtos().add(this.functionMetadataTemplateServices.fetchById(createdFunctionMetadataTemplateModel.getId()));
        }

        return this.fetchById(createdFunctionTemplate.getId());
    }


    @Override
    public List<FunctionTemplateDto> fetchAllFunctionTemplate() {
        List<FunctionTemplateModel> functionTemplateModelList = this.functionTemplateRepository.findAll();

        List<FunctionTemplateDto> functionTemplateDtoList = new ArrayList<>();

        for(FunctionTemplateModel functionTemplateModel: functionTemplateModelList) {
            FunctionTemplateDto functionTemplateDto = this.modelMapper.map(functionTemplateModel, FunctionTemplateDto.class);
            functionTemplateDto.setFunctionMetadataTemplateDtos(this.functionMetadataTemplateServices.fetchByFunctionTemplateId(functionTemplateDto.getId()));

            for(FunctionMetadataTemplateDto functionMetadataTemplateDto: functionTemplateDto.getFunctionMetadataTemplateDtos()) {
                functionMetadataTemplateDto.setFunctionTemplateModelId(functionTemplateModel.getId());
            }

            functionTemplateDtoList.add(functionTemplateDto);

        }

        return functionTemplateDtoList;
    }

    @Override
    public List<FunctionTemplateDto> fetchAllFunctionTemplateByDepartment(String functionDepartment) {
        List<FunctionTemplateModel> functionTemplateModelList = this.functionTemplateRepository.findByFunctionDepartment(functionDepartment);

        List<FunctionTemplateDto> functionTemplateDtoList = new ArrayList<>();

        for(FunctionTemplateModel functionTemplateModel: functionTemplateModelList) {
            FunctionTemplateDto functionTemplateDto = this.modelMapper.map(functionTemplateModel, FunctionTemplateDto.class);
            functionTemplateDto.setFunctionMetadataTemplateDtos(this.functionMetadataTemplateServices.fetchByFunctionTemplateId(functionTemplateDto.getId()));

            for(FunctionMetadataTemplateDto functionMetadataTemplateDto: functionTemplateDto.getFunctionMetadataTemplateDtos()) {
                functionMetadataTemplateDto.setFunctionTemplateModelId(functionTemplateModel.getId());
            }

            functionTemplateDtoList.add(functionTemplateDto);

        }

        return functionTemplateDtoList;
    }

    @Override
    public List<FunctionTemplateDto> fetchByTaskTemplateModelId(Integer taskTemplateModelId) {
        List<FunctionTemplateModel> functionTemplateModelList = this.functionTemplateRepository.findByTaskTemplateModelId(taskTemplateModelId);

        List<FunctionTemplateDto> functionTemplateDtoList = new ArrayList<>();

        for(FunctionTemplateModel functionTemplateModel: functionTemplateModelList) {
            FunctionTemplateDto functionTemplateDto = this.modelMapper.map(functionTemplateModel, FunctionTemplateDto.class);
            functionTemplateDto.setFunctionMetadataTemplateDtos(this.functionMetadataTemplateServices.fetchByFunctionTemplateId(functionTemplateDto.getId()));

            for(FunctionMetadataTemplateDto functionMetadataTemplateDto: functionTemplateDto.getFunctionMetadataTemplateDtos()) {
                functionMetadataTemplateDto.setFunctionTemplateModelId(functionTemplateModel.getId());
            }

            functionTemplateDtoList.add(functionTemplateDto);

        }

        return functionTemplateDtoList;
    }

    @Override
    public FunctionTemplateDto fetchById(Integer functionTemplateId) {
        FunctionTemplateModel functionTemplateModel = this.functionTemplateRepository.findById(functionTemplateId)
                .orElseThrow(() -> new ResourceNotFoundException("FunctionTemplate", "id", functionTemplateId));
        FunctionTemplateDto functionTemplateDto = this.modelMapper.map(functionTemplateModel, FunctionTemplateDto.class);
        functionTemplateDto.setFunctionMetadataTemplateDtos(this.functionMetadataTemplateServices.fetchByFunctionTemplateId(functionTemplateId));

        return functionTemplateDto;
    }

    @Override
    public FunctionTemplateDto updateFunctionTemplate(FunctionTemplateDto functionTemplateDto, Integer functionTemplateId) {
        FunctionTemplateModel existingFunctionTemplateModel = this.modelMapper.map(this.fetchById(functionTemplateId), FunctionTemplateModel.class);
        existingFunctionTemplateModel.setFunctionTitle(functionTemplateDto.getFunctionTitle());
        existingFunctionTemplateModel.setFunctionDescription(functionTemplateDto.getFunctionDescription());
        existingFunctionTemplateModel.setFunctionDepartment(functionTemplateDto.getFunctionDepartment());
        existingFunctionTemplateModel.setTaskTemplateModel(this.taskTemplateRepository.findById(functionTemplateDto.getTaskTemplateModelId()).orElseThrow(() -> new ResourceNotFoundException("TaskTemplate", "id", functionTemplateDto.getTaskTemplateModelId())));

        this.functionTemplateRepository.save(existingFunctionTemplateModel);

        return this.fetchById(functionTemplateId);
    }

    @Override
    public Integer deleteFunctionTemplate(Integer functionTemplateId) {
        if(this.fetchById(functionTemplateId) == null) {
            return -1;
        }
        // Delete the function metadata
        List<FunctionMetadataTemplateDto> functionMetadataTemplateDtoList = this.functionMetadataTemplateServices.fetchByFunctionTemplateId(functionTemplateId);
        for (FunctionMetadataTemplateDto functionMetadataTemplateDto: functionMetadataTemplateDtoList) {
            this.functionMetadataTemplateServices.deleteFunctionMetadata(functionMetadataTemplateDto.getId());
        }

        // Delete the function template
        this.functionTemplateRepository.deleteById(functionTemplateId);
        return functionTemplateId;
    }
}
