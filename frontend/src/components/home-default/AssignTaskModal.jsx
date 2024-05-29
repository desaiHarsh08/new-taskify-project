import React, { useEffect, useState } from 'react'
import { addTask } from '../../apis/taskApis';
import { setShowAlert } from '../../app/features/showAlertSlice';
import { setToggle } from '../../app/features/toogleSlice';
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllUsers } from '../../apis/authApis';
import { selectUser } from '../../app/features/userSlice';

const AssignTaskModal = ({ selectedTaskType, taskPriority, customerInfo, taskInfo }) => {
    const dispatch = useDispatch();

    const [usersArr, setUsersArr] = useState([]);
    const [selectedUser, setSelectedUser] = useState();

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchAllUsers();
            console.log("all users:", data.payload);
            setUsersArr(data.payload);
            setSelectedUser(data.payload[0]);
        })()

        console.log("\n\n\ntaskInfo in assign: ", taskInfo)
    }, [selectedTaskType, taskPriority, customerInfo, taskInfo]);

    const user = useSelector(selectUser);
    console.log("selectedTaskType in assign func:", selectedTaskType, taskPriority);

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
        taskAssignToUserId: -1, // TODO
        functionDtoList: [],
        pumpType: taskInfo.pumpType,
        pumpManufacturer: taskInfo.pumpManufacturer,
        specification: taskInfo.specification,
        problemDescription: taskInfo.problemDescription,
        customerModel: customerInfo
    });

    const handleCreateTask = async () => {
        const tmpSelectedFunctionTemplate = { ...selectedFunctionTemplate }

        // console.log("in create task, tmpSelectedFunctionTemplate:", tmpSelectedFunctionTemplate);

        // Create the task
        const newTask = { ...task };
        newTask.taskTemplateModelId = selectedTaskType.id;
        console.log("newTask.taskTemplateModelId:", newTask.taskTemplateModelId);

        newTask.pumpType = taskInfo.pumpType,
        newTask.pumpManufacturer = taskInfo.pumpManufacturer,
        newTask.specification = taskInfo.specification,
        newTask.problemDescription = taskInfo.problemDescription,


        newTask.customerModel = customerInfo;
        console.log("\n\n\nCustomer info in assign task modal:", customerInfo)

        newTask.taskCreatedByUserId = user.id;
        console.log("selectedUser.id:", selectedUser.id);
        newTask.taskAssignToUserId = selectedUser.id;

        newTask.taskAssignedToDepartment = selectedUser.department;

        const { data: taskResponse, error: taskError } = await addTask(newTask);
        console.log(taskResponse);
        const taskId = taskResponse.payload.id;

        dispatch(setShowAlert({
            flag: true,
            alertType: "success",
            alertMessage: "New Task Created!"
        }))
        dispatch(setToggle());

    }

    return (
        <div className="modal  fade" id="assignTaskModal" aria-hidden="true" aria-labelledby="assignTaskModal" tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content" >
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalToggleLabel2">Assign Task</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div id='create-function-modal-bo dy' className="modal-body modal-dialog-scrollable\">
                        <div className='function-type'>
                            <label htmlFor="" className='fw-bolder'>Assign the task</label>
                            <select
                                value={usersArr.findIndex(ele => ele.email === selectedUser?.email)}
                                onChange={(e) => setSelectedUser(usersArr[e.target.selectedIndex])}
                                className="form-select my-2"
                                aria-label="Default select example"
                            >
                                {usersArr?.map((u, index) => (
                                    <option value={index} className='d-flex gap-3'>
                                        <p className='px-2'>{u.email}</p>
                                        {/* <span className='px-2 badge text-bg-secondary' style={{fontSize: '9px'}}>&nbsp;[{u.department}]</span> */}
                                    </option>
                                ))}
                            </select>
                        </div>

                    </div>
                    <div className="modal-footer">

                        <button className="btn btn-primary" data-bs-target="#taskInfoDetailsModal" data-bs-toggle="modal">Back</button>
                        <button
                            onClick={handleCreateTask}
                            className="btn btn-primary"
                            data-bs-target="#assignTaskModal"
                            data-bs-toggle="modal"
                        >Create</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AssignTaskModal