import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link, Outlet } from 'react-router-dom'
import { selectOnTaskFunctionPage, setOnTaskFunctionPage } from '../app/features/onTaskFunctionPageSlice'
import { selectOnTaskDefaultPage, setOnTaskDefaultPage } from '../app/features/onTaskDefaultPageSlice'
import { selectOnTaskViewPage, setOnTaskViewPage } from '../app/features/onTaskViewPageSlice'
import { selectTaskId } from '../app/features/taskIdSlice'
import { selectFunctionId } from '../app/features/functionIdSlice'

const Tasks = () => {
    const taskId = useSelector(selectTaskId);
    const functionId = useSelector(selectFunctionId);

    const onTaskDefaultPage = useSelector(selectOnTaskDefaultPage);
    const onTaskViewPage = useSelector(selectOnTaskViewPage);
    const onTaskFunctionPage = useSelector(selectOnTaskFunctionPage);

    const dispatch = useDispatch();

    return (
        <div className='h-100 w-100 p-2'>
            <nav aria-label="breadcrumb">
                <ol className="breadcrumb">
                    {onTaskDefaultPage && <li className="breadcrumb-item text-light">
                        <Link className='text-decoration-none text-primary' to={`/home/tasks`}>All Task</Link>
                    </li>}
                    {onTaskViewPage && <li className="breadcrumb-item">
                        <Link to={`/home/tasks/${taskId}`} className='text-decoration-none text-primary'>Task</Link>
                    </li>}
                    {onTaskFunctionPage && <li className="breadcrumb-item" aria-current="page">
                        <Link to={`/home/tasks/${taskId}/${functionId}`} className='text-decoration-none text-primary'>Function</Link>
                    </li>}
                </ol>
            </nav>
            <Outlet />
        </div>
    )
}

export default Tasks