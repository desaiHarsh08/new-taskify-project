package com.taskify.services.impl;

import com.taskify.dtos.FunctionDto;
import com.taskify.dtos.FunctionMetadataDto;
import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.*;
import com.taskify.repositories.*;
import com.taskify.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionServicesImpl implements FunctionServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private FunctionTemplateServices functionTemplateServices;

    @Autowired
    private FunctionMetadataServices functionMetadataServices;

    @Autowired
    private MetaFieldRepository metaFieldRepository;

    @Autowired
    private ActivityLogServices activityLogServices;


    @Override
    public FunctionDto createFunction(FunctionDto functionDto, FunctionTemplateDto functionTemplateDto) {
        System.out.println(functionTemplateDto);
        System.out.println(functionDto);
        // Create FunctionModel
        FunctionModel functionModel = new FunctionModel(
                0,
                functionTemplateDto.getFunctionTitle(),
                functionTemplateDto.getFunctionDescription(),
                functionTemplateDto.getFunctionDepartment(),
                functionDto.getFunctionCreatedDate(),
                this.userRepository.findById(functionDto.getFunctionCreatedByUserId())
                        .orElseThrow(()-> new ResourceNotFoundException("UserModel", "id", functionDto.getFunctionCreatedByUserId())),
                this.userRepository.findById(functionDto.getFunctionAssignedToUserId())
                        .orElseThrow(()-> new ResourceNotFoundException("UserModel", "id", functionDto.getFunctionAssignedToUserId())),
                false,
                null,
                null,
                functionTemplateDto.isDefaultFunction(),
                this.taskRepository.findById(functionDto.getTaskModelId())
                        .orElseThrow(()-> new ResourceNotFoundException("Task", "id", functionDto.getTaskModelId()))
        );
        System.out.println(functionModel);
        Integer functionId = this.functionRepository.save(functionModel).getId();
        functionModel.setId(functionId);

        FunctionTemplateDto functionTemplateDtofetch = this.functionTemplateServices.fetchById(functionTemplateDto.getId());

        // Create the FunctionMetadata
        for (int i = 0; i < functionTemplateDtofetch.getFunctionMetadataTemplateDtos().size(); i++) {
            FunctionMetadataTemplateDto functionMetadataTemplateDto = functionTemplateDto.getFunctionMetadataTemplateDtos().get(i);

            FunctionMetadataDto functionMetadataDto = new FunctionMetadataDto();
            functionMetadataDto.setFunctionMetadataTemplateId(functionMetadataTemplateDto.getId());
            functionMetadataDto.setFunctionModelId(functionModel.getId());
            functionMetadataDto.setMetadataTitle(functionMetadataTemplateDto.getMetadataTitle());
            functionMetadataDto.setMetadataDescription(functionMetadataTemplateDto.getMetadataDescription());
            functionMetadataDto.setFunctionMetadataFinishedByUserId(-1);
            functionMetadataDto.setMetadataAssignedToUserId(functionDto.getFunctionAssignedToUserId());
            functionMetadataDto.setFunctionMetadataDone(false);
            functionMetadataDto.setFunctionMetadataFinishedDate(null);

            FunctionMetadataDto createdFunctionMetadataDto = this.functionMetadataServices.createFunctionMetadata(functionMetadataDto);
        }


        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "FUNCTION",
                "CREATE",
                "Function (" + functionDto.getFunctionTitle() + ") got created by " + functionModel.getFunctionCreatedByUser().getName()
        );

        this.activityLogServices.createLog(activityLogModel);


        return this.fetchByFunctionId(functionId);
    }

    @Override
    public FunctionDto createDefaultFunction(FunctionDto functionDto, FunctionTemplateDto functionTemplateDto) {
        System.out.println(functionTemplateDto);
        System.out.println(functionDto);
        // Create FunctionModel
        FunctionModel functionModel = new FunctionModel(
                0,
                functionTemplateDto.getFunctionTitle(),
                functionTemplateDto.getFunctionDescription(),
                functionTemplateDto.getFunctionDepartment(),
                functionDto.getFunctionCreatedDate(),
                this.userRepository.findById(functionDto.getFunctionCreatedByUserId())
                        .orElseThrow(()-> new ResourceNotFoundException("UserModel", "id", functionDto.getFunctionCreatedByUserId())),
                this.userRepository.findById(functionDto.getFunctionAssignedToUserId())
                        .orElseThrow(()-> new ResourceNotFoundException("UserModel", "id", functionDto.getFunctionAssignedToUserId())),
                false,
                null,
                null,
                functionTemplateDto.isDefaultFunction(),
                this.taskRepository.findById(functionDto.getTaskModelId())
                        .orElseThrow(()-> new ResourceNotFoundException("Task", "id", functionDto.getTaskModelId()))
        );
        System.out.println(functionModel);
        Integer functionId = this.functionRepository.save(functionModel).getId();
        functionModel.setId(functionId);

        return this.fetchByFunctionId(functionId);
    }

    @Override
    public List<FunctionDto> fetchAllFunctions() {
        List<FunctionModel> functionModelList = this.functionRepository.findAll();

        List<FunctionDto> functionDtoList = new ArrayList<>();

        for (FunctionModel functionModel: functionModelList) {
            FunctionDto functionDto = this.modelMapper.map(functionModel, FunctionDto.class);

            functionDto.setFunctionCreatedByUserId(functionModel.getFunctionCreatedByUser().getId());
            functionDto.setFunctionAssignedToUserId(functionModel.getFunctionAssignedToUser().getId());
            functionDto.setFunctionFinishedByUserId(functionModel.getFunctionFinishedByUser().getId());
            functionDto.setTaskModelId(functionModel.getTaskModel().getId());


            functionDto.setFunctionMetadataDtoList(this.functionMetadataServices.fetchByFunctionId(functionModel.getId()));

            functionDtoList.add(functionDto);
        }

        return functionDtoList;
    }

    @Override
    public List<FunctionDto> fetchAllFunctionDone() {
        List<FunctionModel> functionModelList = this.functionRepository.findByIsFunctionDone(true);

        List<FunctionDto> functionDtoList = new ArrayList<>();

        for(FunctionModel functionModel: functionModelList) {
            FunctionDto functionDto = this.modelMapper.map(functionModel, FunctionDto.class);

            functionDto.setFunctionCreatedByUserId(functionModel.getFunctionCreatedByUser().getId());
            functionDto.setFunctionAssignedToUserId(functionModel.getFunctionAssignedToUser().getId());
            functionDto.setFunctionFinishedByUserId(functionModel.getFunctionFinishedByUser().getId());
            functionDto.setTaskModelId(functionModel.getTaskModel().getId());

            functionDto.setFunctionMetadataDtoList(this.functionMetadataServices.fetchByFunctionId(functionDto.getId()));

            functionDtoList.add(functionDto);
        }

        return functionDtoList;
    }

    @Override
    public FunctionDto fetchByFunctionId(Integer functionId) {
        FunctionModel functionModel = this.functionRepository.findById(functionId)
                .orElseThrow(()-> new ResourceNotFoundException("FunctionModel", "id", functionId));
        FunctionDto functionDto = this.modelMapper.map(functionModel, FunctionDto.class);

        functionDto.setFunctionCreatedByUserId(functionModel.getFunctionCreatedByUser().getId());
        functionDto.setFunctionAssignedToUserId(functionModel.getFunctionAssignedToUser().getId());
        if(functionModel.getFunctionFinishedByUser() != null) {
            functionDto.setFunctionFinishedByUserId(functionModel.getFunctionFinishedByUser().getId());
        }
        functionDto.setTaskModelId(functionModel.getTaskModel().getId());

        if(functionDto == null) {
            throw new ResourceNotFoundException("Function", "id", functionId);
        }
        functionDto.setFunctionMetadataDtoList(this.functionMetadataServices.fetchByFunctionId(functionId));

        return functionDto;
    }

    @Override
    public List<FunctionDto> fetchByTaskId(Integer taskId) {
        TaskModel taskModel = this.modelMapper.map(this.taskRepository.findById(taskId), TaskModel.class);
        List<FunctionModel> functionModelList = this.functionRepository.findByTaskModel(taskModel);

        List<FunctionDto> functionDtoList = new ArrayList<>();

        for (FunctionModel functionModel: functionModelList) {
            FunctionDto functionDto = this.modelMapper.map(functionModel, FunctionDto.class);

            functionDto.setFunctionCreatedByUserId(functionModel.getFunctionCreatedByUser().getId());
            functionDto.setFunctionAssignedToUserId(functionModel.getFunctionAssignedToUser().getId());
            if(functionModel.getFunctionFinishedByUser() != null) {
                functionDto.setFunctionFinishedByUserId(functionModel.getFunctionFinishedByUser().getId());
            }
            functionDto.setTaskModelId(functionModel.getTaskModel().getId());

            functionDto.setFunctionMetadataDtoList(this.functionMetadataServices.fetchByFunctionId(functionDto.getId()));

            functionDtoList.add(functionDto);
        }

        return functionDtoList;
    }

    @Override
    public void markFunctionDone(Integer functionId, Integer functionFinishedByUserId, Date finishedDate) {
        FunctionModel functionModel = this.functionRepository.findById(functionId)
                .orElseThrow(()-> new ResourceNotFoundException("FunctionModel", "id", functionId));

        UserModel userModel = this.userRepository.findById(functionFinishedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", functionFinishedByUserId));

        functionModel.setFunctionDone(true);
        functionModel.setFunctionFinishedByUser(userModel);
        functionModel.setFunctionFinishedDate(finishedDate);

        this.functionRepository.save(functionModel);

        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "FUNCTION",
                "COMPLETE",
                "Function (" + functionModel.getFunctionTitle() + ") got completed by " + functionModel.getFunctionFinishedByUser().getName()
        );

        this.activityLogServices.createLog(activityLogModel);

    }



    @Override
    public FunctionDto updateFunction(FunctionModel functionModel) {
        if(this.fetchByFunctionId(functionModel.getId()) == null ) {
            return null;
        }
        FunctionDto updatedFunctionDto = this.modelMapper.map(this.functionRepository.save(functionModel), FunctionDto.class);
        updatedFunctionDto.setFunctionMetadataDtoList(this.functionMetadataServices.fetchByFunctionId(functionModel.getId()));

        updatedFunctionDto.setFunctionCreatedByUserId(functionModel.getFunctionCreatedByUser().getId());
        updatedFunctionDto.setFunctionAssignedToUserId(functionModel.getFunctionAssignedToUser().getId());
        updatedFunctionDto.setFunctionFinishedByUserId(functionModel.getFunctionFinishedByUser().getId());
        updatedFunctionDto.setTaskModelId(functionModel.getTaskModel().getId());

        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "FUNCTION",
                "UPDATE",
                "Function (" + functionModel.getFunctionTitle() + ") got updated by " + functionModel.getFunctionAssignedToUser().getName()
        );

        this.activityLogServices.createLog(activityLogModel);

        return updatedFunctionDto;
    }

    @Override
    public Integer deleteFunction(Integer functionId) {
        FunctionModel functionModel = this.functionRepository.findById(functionId).orElseThrow(()->new ResourceNotFoundException("FunctionModel", "id", functionId));
        if(this.fetchByFunctionId(functionId) == null ) {
            return -1;
        }
        System.out.println("functionId:" + functionId);
        // Delete all the functionMetadata
        List<FunctionMetadataDto> functionMetadataDtoList = this.functionMetadataServices.fetchByFunctionId(functionId);
        System.out.println("In function services impl, functionMetadataDtoList: " + functionMetadataDtoList);

        for (FunctionMetadataDto functionMetadataDto: functionMetadataDtoList) {
            this.functionMetadataServices.deleteFunctionMetadata(functionMetadataDto.getId());
        }
        // Delete the functionModel
        this.functionRepository.deleteById(functionId);


        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "FUNCTION",
                "DELETE",
                "Function (" + functionModel.getFunctionTitle() + ") got deleted by " + functionModel.getFunctionAssignedToUser().getName()
        );

        this.activityLogServices.createLog(activityLogModel);

        return functionId;
    }
}
