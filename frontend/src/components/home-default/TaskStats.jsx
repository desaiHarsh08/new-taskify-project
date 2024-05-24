import React from "react"
import { GoTasklist } from "react-icons/go";
import { MdOutlineCreateNewFolder, MdOutlinePriorityHigh, MdOutlineTaskAlt } from "react-icons/md";
import { MdOutlinePending } from "react-icons/md";

const TaskStats = ({ allTasks }) => {
    const getPendingTask = () => {
        const tmpArr = allTasks.filter((ele) => ele.taskCompleted === false);
        console.log(tmpArr.length)
        return tmpArr.length;
    }

    const getHighPriorityTask = () => {
        const tmpArr = allTasks.filter((ele) => ele.taskPriority === "HIGH");
        console.log(tmpArr.length)
        return tmpArr.length;
    }
    
    const getCompletedTask = () => {
        const tmpArr = allTasks.filter((ele) => ele.taskCompleted === true);
        console.log(tmpArr.length)
        return tmpArr.length;
    }

    return (
        <div className={`${window.innerWidth > 640 ? 'container': 'mt-5'} row flex-column gap-2 w-100 mx-auto mb-5`}>
            <div className="col card px-0">
                <h5 className="card-header">May 15, 2024</h5>
                <div className="card-body">
                    <h5 className="card-title d-flex gap-2 align-items-center">
                        <span>Total Task</span>
                        <MdOutlineCreateNewFolder className="fw-bold text-primary" />
                    </h5>
                    <p className="card-text">{allTasks.length}</p>
                    
                </div>
            </div>
            <div className="col card px-0">
                <h5 className="card-header">May 15, 2024</h5>
                <div className="card-body">
                    <h5 className="card-title d-flex gap-2 align-items-center ">
                        <span>Completed</span>
                        <MdOutlineTaskAlt className="text-success" />
                    </h5>
                    <p className="card-text">{getCompletedTask()}</p>
                    
                </div>
            </div>
            <div className="col card px-0">
                <h5 className="card-header">May 15, 2024</h5>
                <div className="card-body">
                    <h5 className="card-title d-flex align-items-center gap-2">
                        <span>Pending</span>
                    <MdOutlinePending />
                    </h5>
                    <p className="card-text">{getPendingTask()}</p>
                    
                </div>
            </div>
            <div className="col card px-0">
                <h5 className="card-header">May 15, 2024</h5>
                <div className="card-body">
                    <h5 className="card-title">
                        <span>High Priority</span>
                        <MdOutlinePriorityHigh className="text-danger" />
                    </h5>
                    <p className="card-text ">{getHighPriorityTask()}</p>
                    
                </div>
            </div>
        </div>
    )
}

export default TaskStats