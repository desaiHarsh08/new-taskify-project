import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { selectOnTaskDefaultPage, setOnTaskDefaultPage } from '../app/features/onTaskDefaultPageSlice';
import { selectOnTaskViewPage, setOnTaskViewPage } from '../app/features/onTaskViewPageSlice';
import { selectOnTaskFunctionPage, setOnTaskFunctionPage } from '../app/features/onTaskFunctionPageSlice';
import { fetchTaskbyId } from '../apis/taskApis';
import { selectTaskId, setTaskId } from '../app/features/taskIdSlice';
import { Outlet, useParams } from 'react-router-dom';

const TaskView = () => {
    const dispatch = useDispatch();

    const taskId = useSelector(selectTaskId);
    const { taskId: taskIdParams } = useParams();
    console.log("taskIdParams:", taskIdParams)

    if(taskId === -1) {
        dispatch(setTaskId(taskIdParams));
        console.log("taskIdParams:", taskIdParams)
    }

    const [task, setTask] = useState();

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchTaskbyId(taskId);
            console.log(data.payload);
            setTask(data.payload);
        })();
        dispatch(setOnTaskDefaultPage(true));
        dispatch(setOnTaskViewPage(true));
        dispatch(setOnTaskFunctionPage(false));
    }, []);

    return (
        <div className='w-100 h-100 '>
            <Outlet />
        </div>
    )
}

export default TaskView