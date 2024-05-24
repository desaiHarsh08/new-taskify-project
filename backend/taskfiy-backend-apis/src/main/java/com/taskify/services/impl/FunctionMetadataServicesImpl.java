package com.taskify.services.impl;

import com.taskify.dtos.FunctionMetadataDto;
import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.*;
import com.taskify.repositories.FunctionMetadataRepository;
import com.taskify.repositories.FunctionRepository;
import com.taskify.repositories.TaskRepository;
import com.taskify.repositories.UserRepository;
import com.taskify.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionMetadataServicesImpl implements FunctionMetadataServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FunctionMetadataRepository functionMetadataRepository;

    @Autowired
    private FunctionMetadataTemplateServices functionMetadataTemplateServices;

    @Autowired
    private MetaFieldServices metaFieldServices;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ActivityLogServices activityLogServices;


    @Override
    public FunctionMetadataDto createFunctionMetadata(FunctionMetadataDto functionMetadataDto) {
        System.out.println("In function metadata services impl, functionMetadataDto: " + functionMetadataDto);
        // Fetch the functionMetadataTemplate
        FunctionMetadataTemplateDto functionMetadataTemplateDto = this.functionMetadataTemplateServices.fetchById(functionMetadataDto.getFunctionMetadataTemplateId());
        System.out.println("function template: " + functionMetadataTemplateDto);

        // Create the functionMetadataModel
        FunctionMetadataModel functionMetadataModel = new FunctionMetadataModel();
        functionMetadataModel.setMetadataTitle(functionMetadataTemplateDto.getMetadataTitle());
        functionMetadataModel.setMetadataDescription(functionMetadataTemplateDto.getMetadataDescription());
        functionMetadataModel.setFunctionModel(this.functionRepository.findById(functionMetadataDto.getFunctionModelId())
                .orElseThrow(() -> new ResourceNotFoundException("FunctionModel", "id", functionMetadataDto.getFunctionModelId())));
        functionMetadataModel.setMetadataAssignedToUser(this.userRepository.findById(functionMetadataDto.getMetadataAssignedToUserId())
                .orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", functionMetadataDto.getMetadataAssignedToUserId())));

        FunctionMetadataModel createdFunctionMetadata = this.functionMetadataRepository.save(functionMetadataModel);

        functionMetadataModel.setId(createdFunctionMetadata.getId());

        // Create the metafields
        List<MetaFieldModel> metaFieldModelList = this.metaFieldServices.createMetaField(functionMetadataTemplateDto, createdFunctionMetadata);
        System.out.println("created meta fields list: " + metaFieldModelList);
        functionMetadataDto.setMetaFieldModelList(metaFieldModelList);

        System.out.println("overall function metadata dto :" + functionMetadataDto);

        return functionMetadataDto;
    }

    @Override
    public FunctionMetadataDto createDefaultFunctionMetadata(FunctionMetadataDto functionMetadataDto) {
        System.out.println("In function metadata services impl, functionMetadataDto: " + functionMetadataDto);
        // Fetch the functionMetadataTemplate
        FunctionMetadataTemplateDto functionMetadataTemplateDto = this.functionMetadataTemplateServices.fetchById(functionMetadataDto.getFunctionMetadataTemplateId());
        System.out.println("function template: " + functionMetadataTemplateDto);

        // Create the functionMetadataModel
        FunctionMetadataModel functionMetadataModel = new FunctionMetadataModel();
        functionMetadataModel.setMetadataTitle(functionMetadataTemplateDto.getMetadataTitle());
        functionMetadataModel.setMetadataDescription(functionMetadataTemplateDto.getMetadataDescription());
        functionMetadataModel.setFunctionModel(this.functionRepository.findById(functionMetadataDto.getFunctionModelId())
                .orElseThrow(() -> new ResourceNotFoundException("FunctionModel", "id", functionMetadataDto.getFunctionModelId())));
        functionMetadataModel.setMetadataAssignedToUser(this.userRepository.findById(functionMetadataDto.getMetadataAssignedToUserId())
                .orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", functionMetadataDto.getMetadataAssignedToUserId())));

        FunctionMetadataModel createdFunctionMetadata = this.functionMetadataRepository.save(functionMetadataModel);

        functionMetadataModel.setId(createdFunctionMetadata.getId());

        return this.fetchByFunctionMetadataId(functionMetadataModel.getId());
    }

    @Override
    public List<FunctionMetadataDto> fetchAllFunctionMetadata() {
        List<FunctionMetadataDto> functionMetadataDtoList = new ArrayList<>();

        // Fetch all functionMetadata
        List<FunctionMetadataModel> functionMetadataModelList = this.functionMetadataRepository.findAll();
        for (FunctionMetadataModel functionMetadataModel: functionMetadataModelList) {
            FunctionMetadataDto functionMetadataDto = this.modelMapper.map(functionMetadataModel, FunctionMetadataDto.class);
            // Set all the respective metafields
            functionMetadataDto.setMetaFieldModelList(this.metaFieldServices.fetchByFunctionMetadataId(functionMetadataDto.getId()));
            functionMetadataDtoList.add(functionMetadataDto);
        }

        return functionMetadataDtoList;
    }

    @Override
    public List<FunctionMetadataDto> fetchAllFunctionMetadataDone() {
        List<FunctionMetadataModel> functionMetadataModelList = this.functionMetadataRepository.findByIsFunctionMetadataDone(true);

        List<FunctionMetadataDto> functionMetadataDtoList = new ArrayList<>();

        for (FunctionMetadataModel functionMetadataModel: functionMetadataModelList) {
            FunctionMetadataDto functionMetadataDto = this.modelMapper.map(functionMetadataModel, FunctionMetadataDto.class);

            functionMetadataDto.setMetadataAssignedToUserId(functionMetadataModel.getMetadataAssignedToUser().getId());
            functionMetadataDto.setFunctionMetadataFinishedByUserId(functionMetadataModel.getFunctionMetadataFinishedByUser().getId());
            functionMetadataDto.setFunctionModelId(functionMetadataModel.getFunctionModel().getId());
            // functionMetadataDto.setFunctionMetadataTemplateId(functionMetadataModel.); // will be set in the above order


            functionMetadataDto.setMetaFieldModelList(this.metaFieldServices.fetchByFunctionMetadataId(functionMetadataDto.getId()));
            functionMetadataDtoList.add(functionMetadataDto);
        }

        return functionMetadataDtoList;
    }

    @Override
    public FunctionMetadataDto fetchByFunctionMetadataId(Integer functionMetadataId) {
        FunctionMetadataModel functionMetadataModel = this.functionMetadataRepository.findById(functionMetadataId)
                .orElseThrow(()-> new ResourceNotFoundException("FunctionModel", "id", functionMetadataId));

        FunctionMetadataDto functionMetadataDto = this.modelMapper.map(functionMetadataModel, FunctionMetadataDto.class);
        if(functionMetadataModel.getFunctionMetadataFinishedByUser() != null) {
            functionMetadataDto.setFunctionMetadataFinishedByUserId(functionMetadataModel.getFunctionMetadataFinishedByUser().getId());
        }
        functionMetadataDto.setMetadataAssignedToUserId(functionMetadataModel.getMetadataAssignedToUser().getId());
        functionMetadataDto.setFunctionModelId(functionMetadataModel.getFunctionModel().getId());
        // function metadata template model id, -> will set above order



        if (functionMetadataDto == null) {
            throw new ResourceNotFoundException("Function metadata", "id", functionMetadataId);
        }
        functionMetadataDto.setMetaFieldModelList(this.metaFieldServices.fetchByFunctionMetadataId(functionMetadataId));
        return functionMetadataDto;
    }

    @Override
    public List<FunctionMetadataDto> fetchByFunctionId(Integer functionId) {
        FunctionModel functionModel = this.modelMapper.map(this.functionRepository.findById(functionId), FunctionModel.class);
        if(functionModel == null) {
            throw new ResourceNotFoundException("Function", "id", functionId);
        }
        List<FunctionMetadataModel> functionMetadataModelList = this.functionMetadataRepository.findByFunctionModel(functionModel);

        List<FunctionMetadataDto> functionMetadataDtoList = new ArrayList<>();

        for (FunctionMetadataModel functionMetadataModel: functionMetadataModelList) {
            FunctionMetadataDto functionMetadataDto = this.modelMapper.map(functionMetadataModel, FunctionMetadataDto.class);

            functionMetadataDto.setMetadataAssignedToUserId(functionMetadataModel.getMetadataAssignedToUser().getId());
            if(functionMetadataModel.getFunctionMetadataFinishedByUser() != null) {
                functionMetadataDto.setFunctionMetadataFinishedByUserId(functionMetadataModel.getFunctionMetadataFinishedByUser().getId());
            }
            functionMetadataDto.setFunctionModelId(functionMetadataModel.getFunctionModel().getId());
            // functionMetadataDto.setFunctionMetadataTemplateId(functionMetadataModel.); // will be set in the above


            functionMetadataDto.setMetaFieldModelList(this.metaFieldServices.fetchByFunctionMetadataId(functionMetadataDto.getId()));
            functionMetadataDtoList.add(functionMetadataDto);
        }

        return functionMetadataDtoList;
    }

    @Override
    public void markFunctionMetadataDone(Integer functionMetadataId, Integer finishedByUserId, Date finishedDate) {
        System.out.println("finishedDate: " + finishedDate);
        FunctionMetadataModel functionMetadataModel = this.functionMetadataRepository.findById(functionMetadataId)
                .orElseThrow(() -> new ResourceNotFoundException("FunctionMetadataModel", "id", functionMetadataId));

        

        UserModel userModel = this.userRepository.findById(finishedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", finishedByUserId));


        functionMetadataModel.setFunctionMetadataDone(true);
        functionMetadataModel.setFunctionMetadataFinishedByUser(userModel);
        functionMetadataModel.setFunctionMetadataFinishedDate(finishedDate);
        System.out.println(functionMetadataModel.getFunctionMetadataFinishedDate());

        FunctionMetadataModel updatedFunctionMetadataModel = this.functionMetadataRepository.save(functionMetadataModel);

        // Mark the function as done, if all metadata as done
        List<FunctionMetadataModel> functionMetadataModelList = this.functionMetadataRepository.findByFunctionModel(functionMetadataModel.getFunctionModel());
        for (FunctionMetadataModel f: functionMetadataModelList) {
            if(!f.isFunctionMetadataDone()) {
                return;
            }
        }
        functionMetadataModel.getFunctionModel().setFunctionFinishedDate(finishedDate);
        functionMetadataModel.getFunctionModel().setFunctionDone(true);
        functionMetadataModel.getFunctionModel().setFunctionFinishedByUser(functionMetadataModel.getMetadataAssignedToUser());

        this.functionRepository.save(functionMetadataModel.getFunctionModel());

        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "FUNCTION",
                "COMPLETE",
                "Function (" + functionMetadataModel.getFunctionModel().getFunctionTitle() + ") got completed by " + functionMetadataModel.getFunctionModel().getFunctionAssignedToUser().getName()
        );

        this.activityLogServices.createLog(activityLogModel);


    }

    @Override
    public FunctionMetadataDto updateFunctionMetadata(FunctionMetadataModel functionMetadataModel) {
        if(this.fetchByFunctionId(functionMetadataModel.getId()) == null) {
            return null;
        }
        FunctionMetadataDto functionMetadataDto = this.modelMapper.map(this.functionMetadataRepository.save(functionMetadataModel), FunctionMetadataDto.class);
        functionMetadataDto.setMetaFieldModelList(this.metaFieldServices.fetchByFunctionMetadataId(functionMetadataDto.getId()));
        return functionMetadataDto;
    }

    @Override
    public void forwardMetadata(Integer assignedUserId, Integer functionMetadataModelId) {
        UserModel userModel = this.userRepository.findById(assignedUserId).orElseThrow(()->new ResourceNotFoundException("UserModel", "id", assignedUserId));
        FunctionMetadataModel functionMetadataModel = this.functionMetadataRepository.findById(functionMetadataModelId).orElseThrow(()-> new ResourceNotFoundException("FunctionMetadataModel", "id", functionMetadataModelId));

        // Assign the functionMetadataModel to the assignedUserId
        functionMetadataModel.setMetadataAssignedToUser(userModel);

        // Assign the functionModel to the assignedUserId
        functionMetadataModel.getFunctionModel().setFunctionAssignedToUser(userModel);

        // Assign the taskModel to the assignedUserId
        functionMetadataModel.getFunctionModel().getTaskModel().setTaskAssignedToDepartment(userModel.getDepartment());
        functionMetadataModel.getFunctionModel().getTaskModel().setTaskAssignedToDepartmentDate(new java.sql.Date(System.currentTimeMillis()));

        this.taskRepository.save(functionMetadataModel.getFunctionModel().getTaskModel());
        this.functionRepository.save(functionMetadataModel.getFunctionModel());
        this.functionMetadataRepository.save(functionMetadataModel);

    }

    @Override
    public Integer deleteFunctionMetadata(Integer functionMetadataId) {

        FunctionMetadataModel functionMetadataModel = this.functionMetadataRepository.findById(functionMetadataId)
                .orElseThrow(() -> new ResourceNotFoundException("FunctionMetadataModel", "id", functionMetadataId));

        if(this.fetchByFunctionMetadataId(functionMetadataId) == null) {
            return -1;
        }
        // Delete all the meta fields
        List<MetaFieldModel> metaFieldModelList = this.metaFieldServices.fetchByFunctionMetadataId(functionMetadataId);
        for (MetaFieldModel metaFieldModel: metaFieldModelList) {
            this.metaFieldServices.deleteMetaField(metaFieldModel.getId());
        }
        // Delete the function metadata
        this.functionMetadataRepository.deleteById(functionMetadataId);


        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "FUNCTION",
                "DELETE",
                "Function (" + functionMetadataModel.getFunctionModel().getFunctionTitle() + ") got deleted by " + functionMetadataModel.getFunctionModel().getFunctionAssignedToUser().getName()
        );

        this.activityLogServices.createLog(activityLogModel);


        return functionMetadataId;
    }
}