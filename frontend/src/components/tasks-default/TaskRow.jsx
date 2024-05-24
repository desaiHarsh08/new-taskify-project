import React from 'react'
import { getFormattedDate } from '../../utils/helper'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import { setTaskId } from '../../app/features/taskIdSlice'

const TaskRow = ({ index, task }) => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleTaskClick = (taskId) => {
        dispatch(setTaskId(taskId));
        navigate(`${taskId}`, { replace: true });
    }

    return (
        <tr key={`task-${index}`} onClick={() => handleTaskClick(task.id)} className='table table-light' style={{cursor: "pointer", fontSize: "13px"}}>
            <th scope="row" className='text-center '>
                <p className="text-sm text-center m-0 py-2">{index + 1}</p>
            </th>
            <th scope="row" className='text-center'>
                <p className="text-sm text-center m-0 py-2">{task.id}</p>
            </th>
            <th scope="row" className='text-center'>
                {console.log(task?.taskTemplateModelId)}
                <p className="text-sm text-center m-0 py-2">{task.taskTemplateModelId === 2 ? "New Enquiry" : "Repair Service"}</p>
            </th>
            <td className='text-center'>
                <p className="text-sm text-center m-0 py-2">{task.taskPriority}</p>
            </td>
            <td className='text-center'>
                <p className="text-sm text-center m-0 py-2">{getFormattedDate(task.taskCreatedDate)}</p>
                
            </td>
            <td className='text-center'>
                <p className="text-sm text-center m-0 py-2">
                    {task.taskCompleted ?
                        <span className='bg-success text-light' style={{ fontSize: "12px" }}>CLOSED</span>
                        : <span className='bg-warning text-sm' style={{ fontSize: "12px" }}>IN PROGRESS</span>
                    }
                </p>
            </td>
            <td className='text-center'>
                <p className="text-center ">
                    {task.taskFinishedDate !== null ? getFormattedDate(task.taskFinishedDate) : '-'}
                </p>
            </td>
            <td className='text-center'>
                <button type="button" className="btn btn-sm btn-danger px-4">Delete</button>
            </td>
        </tr>
    )
}

export default TaskRow