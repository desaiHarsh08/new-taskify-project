import React, { useEffect, useState } from 'react'
import { getAllFunctionTemplatesByTaskTemplateId } from '../../apis/functionTemplateApis';
import { useDispatch, useSelector } from 'react-redux';
import { selectUser } from '../../app/features/userSlice';
import { addFunction } from '../../apis/functionApis';
import { fetchTaskbyId } from '../../apis/taskApis';
import { selectFunctionArr, setFunctionArr } from '../../app/features/functionArrSlice';
import { setShowAlert } from '../../app/features/showAlertSlice';

const AddFunctionModal = ({ taskTemplateModelId, taskId }) => {
    const dispatch = useDispatch();
    
    const user = useSelector(selectUser);
    const functionArr = useSelector(selectFunctionArr);

    const [functionTemplateArr, setunctionTemplateArr] = useState([]);
    const [selectedFunctionTemplate, setSelectedFunctionTemplate] = useState();
    const [mySelectedIndex, setMySelectedIndex] = useState();

    useEffect(() => {
        (async () => {
            const { data, error } = await getAllFunctionTemplatesByTaskTemplateId(taskTemplateModelId);
            if (data) {
                console.log("ue of add", data.payload);
                setunctionTemplateArr(data.payload);
                setSelectedFunctionTemplate(data.payload[0]);
                setMySelectedIndex(0);
            }
        })();
    }, []);

    const handleAddFunction = async () => {
        for(let i = 0; i < functionArr.length; i++) {
            for(let j = 0; j < functionArr[i].functionMetadataDtoList.length; j++) {
                if(!functionArr[i].functionMetadataDtoList[j].functionMetadataDone) {
                    dispatch(setShowAlert({
                        flag: true,
                        alertType: "danger", 
                        alertMessage: "Cannot add another function while still pending previous function."
                    }))
                    return;
                }
            }
        }

        const newFunction = {
            functionTitle: selectedFunctionTemplate.functionTitle,
            functionDescription: selectedFunctionTemplate.functionDescription,
            functionDepartment: selectedFunctionTemplate.functionDepartment,
            functionCreatedDate: new Date(),
            functionCreatedByUserId: user.id,
            functionAssignedToUserId: user.id,
            functionDone: false,
            functionFinishedDate: null,
            functionFinishedByUserId: -1,
            defaultFunction: selectedFunctionTemplate.defaultFunction,
            taskModelId: taskId,
            functionMetadataDtoList: selectedFunctionTemplate.functionMetadataTemplateDtos,
        }

        const { data, error } = await addFunction(newFunction, selectedFunctionTemplate);
        console.log(data.payload, error);

        const { data: newRes, error: newResError } = await fetchTaskbyId(taskId);
            console.log(data.payload);
            // TODO: Fetch the user

            const tmpArr = [];
            for (let i = newRes.payload.functionDtoList.length - 1; i >= 0; i--) {
                tmpArr.push(newRes.payload.functionDtoList[i]);
            }
            dispatch(setFunctionArr(tmpArr));

            dispatch(setShowAlert({
                flag: true,
                alertType: "success", 
                alertMessage: "New function added!"
            }))

    }

    return (
        <div
            className="modal fade"
            id="addFunctionModal"
            aria-hidden="true"
            aria-labelledby="addFunctionModal"
            tabIndex="-1"
        >
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalToggleLabel">Add Function</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <select 
                            value={mySelectedIndex}
                            onChange={(e) => {
                                setSelectedFunctionTemplate(functionTemplateArr[e.target.selectedIndex])
                                setMySelectedIndex(e.target.selectedIndex)
                            }}
                            className="form-select" 
                            aria-label="Default select example"
                        >
                            {functionTemplateArr.map((ele, index) => (
                                <option value={index}>{ele.functionTitle}</option>
                            ))}
                        </select>
                    </div>
                    <div className="modal-footer">
                        <button 
                            className="btn btn-primary" 
                            data-bs-target="#addFunctionModal" 
                            data-bs-toggle="modal"
                            onClick={handleAddFunction}
                        >Continue</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AddFunctionModal