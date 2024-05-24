import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { selectTaskId } from '../app/features/taskIdSlice'
import { fetchTaskbyId } from '../apis/taskApis';
import { getFormattedDate } from '../utils/helper';
import { setOnTaskDefaultPage } from '../app/features/onTaskDefaultPageSlice';
import { setOnTaskViewPage } from '../app/features/onTaskViewPageSlice';
import { setOnTaskFunctionPage } from '../app/features/onTaskFunctionPageSlice';
import FunctionCard from '../components/task-view-default/FunctionCard';
import AddFunctionModal from '../components/task-view-default/AddFunctionModal';
import { selectFunctionArr, setFunctionArr } from '../app/features/functionArrSlice';
import { selectIsForwarded } from '../app/features/isForwardedSlice';
import { fetchUserById } from '../apis/authApis';
import DoneTaskModal from '../components/task-view-default/DoneTaskModal';
import { selectToggle } from '../app/features/toogleSlice';

const TaskViewDefault = () => {
    const dispatch = useDispatch();

    const taskId = useSelector(selectTaskId);
    const isForwarded = useSelector(selectIsForwarded);
    const toggle = useSelector(selectToggle);

    const [task, setTask] = useState();
    const [assignee, setAssignee] = useState();
    const functionArr = useSelector(selectFunctionArr);

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchTaskbyId(taskId);
            console.log(data.payload);
            // TODO: Fetch the user
            const { data: assigneeUserResponse, error: assigneeUserError } = await fetchUserById(data.payload.taskAssignToUserId);
            console.log('asignee user:', assigneeUserResponse.payload);
            setAssignee(assigneeUserResponse.payload);

            const tmpArr = [];
            for (let i = data.payload.functionDtoList.length - 1; i >= 0; i--) {
                tmpArr.push(data.payload.functionDtoList[i]);
            }
            dispatch(setFunctionArr(tmpArr));

            setTask(data.payload);

            fetchUser(userId);


        })();

        dispatch(setOnTaskDefaultPage(true));
        dispatch(setOnTaskViewPage(true));
        dispatch(setOnTaskFunctionPage(false));
    }, [isForwarded, toggle]);


    const fetchUser = async (userId) => {
        const { data, error } = await fetchUserById(userId);
        if(data) {
            setAssignee(data.payload);
        }
    }

    return (
        <div>
            <div className='my-2 border-bottom'>
                <h3>Task #{taskId}</h3>
                <ul className='p-2' style={{ listStyle: "none", fontSize: "14px" }}>
                    <div className={`${window.innerWidth > 531 && 'd-flex'} gap-5 align-items-center`}>
                        <li className='d-flex gap-2 align-items-center my-2'>
                            <p className='m-0' style={{minWidth: '100px'}}>Type:</p>
                            <p className='m-0 text-bg-primary rounded-1 p-1' style={{ fontSize: "11px" }}>
                                {task?.taskTemplateModelId === 2 ?
                                    "NEW ENQURY"
                                    : "REPAIR SERVICE"
                                }
                            </p>
                        </li>
                        <li className='d-flex gap-2  my-2'>
                            <p className='m-0' style={{minWidth: '100px'}}>Assignee:</p>
                            <p className='m-0 bg-info p-1 rounded-1' style={{ listStyle: "none", fontSize: "12px" }}>
                                {/* {task?.taskAssignedToDepartment} */}
                                {assignee?.name}
                            </p>
                        </li>
                        <li className='d-flex gap-2  my-2'>
                            <p className='m-0' style={{minWidth: '100px'}}>Status:</p>
                            <p className={`m-0 p-1 rounded-1 ${task?.taskCompleted === true ? "text-bg-success" : "text-bg-warning"}`} style={{ listStyle: "none", fontSize: "12px" }}>
                                {task?.taskCompleted === true ? "CLOSED" : "IN PROGRESS"}
                            </p>
                        </li>

                    </div>
                    <div className="my-3">
                        <li className='d-flex gap-2 '>
                            <p className='m-0' style={{minWidth: '100px'}}>Created Date:</p>
                            <p className='m-0'>{getFormattedDate(task?.taskCreatedDate)}</p>
                        </li>
                        {task?.taskFinishedDate && <li className='d-flex gap-2 '>
                            <p className='m-0' style={{minWidth: '100px'}}>Finished Date:</p>
                            <p className='m-0'>
                                {task?.taskFinishedDate ?
                                    getFormattedDate(task?.taskFinishedDate)
                                    : '-'
                                }
                            </p>
                        </li>}
                        {!task?.taskCompleted && <li>
                            <button 
                                data-bs-target="#doneTaskModal"
                                data-bs-toggle="modal"
                                className='btn btn-danger btn-sm mt-3'
                            >Done</button>
                            <DoneTaskModal taskId={taskId} />
                        </li>}
                    </div>
                </ul>
            </div>
            {!task?.taskCompleted && <div className='my-3'>
                <button
                    className='btn btn-primary'
                    data-bs-toggle="modal"
                    data-bs-target="#addFunctionModal"
                >Add Function</button>
                {task && <AddFunctionModal taskTemplateModelId={task?.taskTemplateModelId} taskId={taskId} />}
            </div>}
            <div className='pb-5 d-flex flex-column gap-3' style={{ height: '45vh', overflow: "auto" }}>
                {console.log(functionArr)}
                {functionArr.map((func, index) => (
                    <FunctionCard func={func} />
                ))}
            </div>
        </div>
    )
}

export default TaskViewDefault