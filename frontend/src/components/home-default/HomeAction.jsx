import React, { useEffect, useState } from 'react'
import { MdLibraryAdd } from 'react-icons/md'
import InputTaskTypeModal from './InputTaskTypeModal'
import InputTaskPriorityModal from './InputTaskPriorityModal'
import { fetchAllTaskTemplates } from '../../apis/taskTemplatesApis'
import CreateFunctionModal from './CreateFunctionModal'
import { useSelector } from 'react-redux'
import { selectUser } from '../../app/features/userSlice'
import AssignTaskModal from './AssignTaskModal'

const HomeAction = ({ allTasks }) => {
    const user = useSelector(selectUser);
    const [taskArr, setTaskArr] = useState([]);
    const [taskPriority, setTaskPriority] = useState("NORMAL");
    const [selectedTaskType, setSelectedTaskType] = useState();

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchAllTaskTemplates();
            if (data !== null) {
                console.log("Task Templates:", data.payload);
                setSelectedTaskType(data.payload[0]);
                setTaskArr(data.payload);
            }
        })();
        console.log(taskArr)
    }, []);

    const getNewServicesTask = () => {
        const tmpArr = allTasks.filter(ele => ele.taskTemplateModelId !== 1);
        return tmpArr.length;
    }

    const getRepairServicesTask = () => {
        const tmpArr = allTasks.filter(ele => ele.taskTemplateModelId === 1);
        return tmpArr.length;
    }

    return (
        <div className={`${window.innerWidth > 640 ? 'd-flex gap-2 h-25' : ''} `}>
            <div id="greet-user" className="card bg-[#5fa65f] p-1" style={window.innerWidth > 640 ? { width: "33%", background: "rgb(182 227 182)" } : { width: "100%", background: "rgb(182 227 182)" }}>
                <h2>Welcome Back!</h2>
                <h5>{user.name}</h5>
            </div>
            <div className={`card ${window.innerWidth < 640 && 'pb-3 my-4'} h-100`} style={window.innerWidth > 640 ?{ width: "33%" } : { width: "100%"}}>
                <p className="card-header">Create Task</p>
                <p className="card-title my-2 gap-2 py-2 px-2 d-flex align-items-center">
                    <MdLibraryAdd className="fs-3" />
                    <span>Add a new task</span>
                </p>
                <div className="px-2">
                    {console.log(taskArr.length)}
                    {taskArr.length > 0 && (
                        <>
                            <InputTaskTypeModal selectedTaskType={selectedTaskType} setSelectedTaskType={setSelectedTaskType} taskArr={taskArr} />
                            <InputTaskPriorityModal taskPriority={taskPriority} setTaskPriority={setTaskPriority} />
                            {/* <CreateFunctionModal selectedTaskType={selectedTaskType} taskPriority={taskPriority} /> */}
                            <AssignTaskModal selectedTaskType={selectedTaskType} taskPriority={taskPriority} />
                            <button
                                className="btn btn-primary"
                                data-bs-target="#inputTaskTypeModal"
                                data-bs-toggle="modal"
                            >Create Task
                            </button>
                        </>
                    )}
                </div>
            </div>
            <div className="card" style={window.innerWidth > 640 ? { width: "33%" } : { width: "100%" }}>
                <p className="card-header">Tasks Recorded</p>
                <div className="m-1 my-3 px-2">
                    <p>New Enquiry: <b>{getNewServicesTask()}</b></p>
                    <p>Service: <b>{getRepairServicesTask()}</b></p>
                </div>
            </div>
        </div>
    )
}

export default HomeAction