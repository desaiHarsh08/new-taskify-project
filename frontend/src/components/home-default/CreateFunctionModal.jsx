import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { selectUser } from '../../app/features/userSlice';
import { addTask } from '../../apis/taskApis';
import { addDefaultFunction } from '../../apis/functionApis';
import { addMetadataDefault } from '../../apis/functionMetadataApis';
import { addDefaultMetafield } from '../../apis/metafieldApis';
import { setShowAlert } from '../../app/features/showAlertSlice';
import { setToggle } from '../../app/features/toogleSlice';

const CreateFunctionModal = ({ selectedTaskType, taskPriority }) => {
    const dispatch = useDispatch();

    const user = useSelector(selectUser);
    console.log("selectedTaskType in create func:", selectedTaskType, taskPriority);

    const [selectedFunctionTemplate, setSelectedFunctionTemplate] = useState(selectedTaskType.functionTemplateDtoList[0]);

    const [task, setTask] = useState({
        id: 0,
        taskTemplateModelId: selectedTaskType.id,
        taskCreatedByUserId: user.id,
        taskCreatedDate: new Date(),
        taskPriority: taskPriority,
        taskAssignedToDepartment: selectedFunctionTemplate.functionDepartment, // TODO 
        taskAssignedToDepartmentDate: new Date(),
        taskCompleted: false,
        taskFinishedDate: null,
        taskFinishedByUserId: -1,
        functionDtoList: [],
    });
    const [functionObj, setFunctionObj] = useState({
        id: 0,
        functionTitle: selectedFunctionTemplate.functionTitle,
        functionDescription: selectedFunctionTemplate.functionDescription,
        functionDepartment: selectedFunctionTemplate.functionDepartment,
        functionCreatedDate: new Date(),
        functionCreatedByUserId: user.id,
        functionAssignedToUserId: user.id,
        isFunctionDone: false,
        functionFinishedDate: null,
        functionFinishedByUserId: -1,
        defaultFunction: selectedFunctionTemplate.defaultFunction,
        taskModelId: -1, // TODO
        functionMetadataDtoList: [],
    });
    const [functionMetadata, setFunctionMetadata] = useState({
        id: 0,
        functionMetadataTemplateId: -1, // TODO
        metadataTitle: "",
        metadataDescription: "",
        metadataAssignedToUserId: user.id,
        functionMetadataDone: false,
        functionMetadataFinishedDate: null,
        functionMetadataFinishedByUserId: -1, // TODO
        functionModelId: -1, // TODO
        metaFieldModelList: []
    });
    const [metafieldArr, setMetafieldArr] = useState([
        {
            id: 0,
            fieldName: "",
            fieldValue: "",
            functionMetadataModel: {}, // TODO
        }
    ]);

    const handleMetafieldChange = (e, metadataIndex) => {
        const { name, value } = e.target;
        const newSelectedFunctionTemplate = { ...selectedFunctionTemplate };
        console.log("metadata to change:", newSelectedFunctionTemplate.functionMetadataTemplateDtos[metadataIndex]);
        let newMetaFieldTemplateModelList = [...newSelectedFunctionTemplate.functionMetadataTemplateDtos[metadataIndex].metaFieldTemplateModelList];

        newMetaFieldTemplateModelList = newMetaFieldTemplateModelList.map((ele) => {
            if (ele.fieldName === name) {
                return { ...ele, fieldValue: value };
            }
            return ele;
        })
        newSelectedFunctionTemplate.functionMetadataTemplateDtos[metadataIndex].metaFieldTemplateModelList = newMetaFieldTemplateModelList;
        setSelectedFunctionTemplate(newSelectedFunctionTemplate);

        console.log("in change, newSelectedFunctionTemplate:", newSelectedFunctionTemplate);
    }

    const handleCreateTask = async (e) => {
        e.preventDefault();
        const tmpSelectedFunctionTemplate = { ...selectedFunctionTemplate }

        console.log("in create task, tmpSelectedFunctionTemplate:", tmpSelectedFunctionTemplate);

        // Create the task
        const newTask = { ...task };
        newTask.taskTemplateModelId = tmpSelectedFunctionTemplate.taskTemplateModelId;
        newTask.taskCreatedByUserId = user.id;
        newTask.taskAssignedToDepartment = tmpSelectedFunctionTemplate.functionDepartment;

        const { data: taskResponse, error: taskError } = await addTask(newTask);
        console.log(taskResponse);
        const taskId = taskResponse.payload.id;

        // Create the function
        const newFunction = {...functionObj}
        newFunction.functionTitle = tmpSelectedFunctionTemplate.functionTitle;
        newFunction.functionDescription = tmpSelectedFunctionTemplate.functionDescription;
        newFunction.functionDepartment = tmpSelectedFunctionTemplate.functionDepartment;
        newFunction.functionCreatedByUserId = user.id;
        newFunction.functionAssignedToUserId = user.id;
        newFunction.isFunctionDone = false;
        newFunction.defaultFunction = tmpSelectedFunctionTemplate.defaultFunction;
        newFunction.taskModelId = taskId;

        const { data: functionResponse, error: functionError } = await addDefaultFunction(newFunction, tmpSelectedFunctionTemplate);
        console.log(functionResponse);
        const functionId = functionResponse.payload.id;

        // Create the functionMetadata
        for(let i = 0; i < tmpSelectedFunctionTemplate.functionMetadataTemplateDtos.length; i++) {
            let functionMetadataTemplate = tmpSelectedFunctionTemplate.functionMetadataTemplateDtos[i];

            let newfunctionMetadata = {...functionMetadata}
            newfunctionMetadata.functionMetadataTemplateId = functionMetadataTemplate.id;
            newfunctionMetadata.metadataTitle = functionMetadataTemplate.metadataTitle;
            newfunctionMetadata.metadataDescription = functionMetadataTemplate.metadataDescription;
            newfunctionMetadata.metadataAssignedToUserId = user.id;
            newfunctionMetadata.functionMetadataDone = false;
            newfunctionMetadata.functionModelId = functionId;

            let { data: metadataResponse, error: metadataError } = await addMetadataDefault(newfunctionMetadata);
            console.log(metadataResponse);
            newfunctionMetadata.id = metadataResponse.payload.id;

            // Create the metafields
            for(let j = 0; j < tmpSelectedFunctionTemplate.functionMetadataTemplateDtos[i].metaFieldTemplateModelList.length; j++) {
                let newMetafield = {
                    id: 0,
                    fieldName: tmpSelectedFunctionTemplate.functionMetadataTemplateDtos[i].metaFieldTemplateModelList[j].fieldName,
                    fieldValue: tmpSelectedFunctionTemplate.functionMetadataTemplateDtos[i].metaFieldTemplateModelList[j].fieldValue
                }
                console.log("Creating newMetafield:", newMetafield, newfunctionMetadata.id);
                let { data: metafieldResponse, error: metafieldError } = await addDefaultMetafield(newMetafield, newfunctionMetadata.id);
                console.log(metafieldResponse, metafieldError);
            }


        }

        dispatch(setShowAlert({
            flag: true,
            alertType: "success",
            alertMessage: "New Task Created!"
        }))
        dispatch(setToggle());
        // console.log("\n\nNew Task Created!");


    }

    
    const handleFunctionChange = async (e) => {
        const tmpSelectedFunctionTemplate = selectedTaskType.functionTemplateDtoList[e.target.selectedIndex];
        const newFunctionObj = {...functionObj};
        newFunctionObj.functionTitle = tmpSelectedFunctionTemplate.functionTitle;
        newFunctionObj.functionDescription = tmpSelectedFunctionTemplate.functionDescription;
        newFunctionObj.functionDepartment = tmpSelectedFunctionTemplate.functionDepartment;
        newFunctionObj.defaultFunction = tmpSelectedFunctionTemplate.defaultFunction;
        

        setFunctionObj(newFunctionObj);
        setSelectedFunctionTemplate(tmpSelectedFunctionTemplate);

        
    }

    return (
        <div className="modal modal-xl fade" id="createFunctionModal" aria-hidden="true" aria-labelledby="inputTaskPriorityModalLabel2" tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content" >
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalToggleLabel2">Create Function</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div id='create-function-modal-body' className="modal-body modal-dialog-scrollable">
                        <div className='function-type'>
                            <label htmlFor="" className='fw-bolder'>Function Type</label>
                            <select
                                // value={selectedFunctionTemplate.functionTitle}
                                onChange={(e) => handleFunctionChange(e)}
                                className="form-select my-2"
                                aria-label="Default select example"
                            >
                                {selectedTaskType.functionTemplateDtoList.map((func, index) => (
                                    <option value={index} className='d-flex gap-3'>
                                        <p className='px-2'>{func.functionTitle}</p>
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className='mb-3'>
                            <p className='fst-italic'>{selectedFunctionTemplate.functionDescription}</p>
                            <p className='fw-bolder mt-5'>Department: <span className='badge text-bg-secondary'>{selectedFunctionTemplate.functionDepartment}</span></p>
                        </div>
                        <div>
                            <h6 className='mt-5 mb-3 fw-bold'>Steps</h6>
                            {selectedFunctionTemplate.functionMetadataTemplateDtos.map((functionMetadataTemplate, functionMetadataIndex) => (
                                <div className="card my-4">
                                    <div className="card-header d-flex gap-2 align-items-center">
                                        <span className='fw-bold'>STEP {functionMetadataIndex + 1}:</span>
                                        <span>{functionMetadataTemplate.metadataTitle}</span>
                                    </div>
                                    <div className="card-text p-3">{functionMetadataTemplate.metadataDescription}</div>
                                    <div className='px-3'>
                                        {functionMetadataTemplate.metaFieldTemplateModelList.map((metafield, index) => (
                                            <div class="mb-3">
                                                <label htmlFor="" class="form-label text-dark">{metafield.fieldName}</label>
                                                <input 
                                                    type="text" 
                                                    name={metafield.fieldName} 
                                                    value={metafield.fieldValue} 
                                                    onChange={(e)=> {handleMetafieldChange(e, functionMetadataIndex)}} 
                                                    class="form-control" placeholder="" 
                                                />
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                    <div className="modal-footer">
                        <button 
                            onClick={handleCreateTask} 
                            className="btn btn-primary" 
                            data-bs-target="#createFunctionModal"
                            data-bs-toggle="modal"
                        >Create</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CreateFunctionModal