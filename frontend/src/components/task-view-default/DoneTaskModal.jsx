import React from 'react'
import { markTaskDone } from '../../apis/taskApis'
import { useDispatch, useSelector } from 'react-redux'
import { selectUser } from '../../app/features/userSlice'
import { setShowAlert } from '../../app/features/showAlertSlice'
import { setToggle } from '../../app/features/toogleSlice'
import { selectFunctionArr } from '../../app/features/functionArrSlice'

const DoneTaskModal = ({ taskId }) => {
    const dispatch = useDispatch();

    const user = useSelector(selectUser);
    const functionArr = useSelector(selectFunctionArr);

    const handleDoneTask = async () => {
        for(let i = 0; i < functionArr.length; i++) {
            for(let j = 0; j < functionArr[i].functionMetadataDtoList.length; j++) {
                if(!functionArr[i].functionMetadataDtoList[j].functionMetadataDone) {
                    dispatch(setShowAlert({
                        flag: true,
                        alertType: "danger", 
                        alertMessage: "Cannot mark the task as done while still pending previous function."
                    }))
                    return;
                }
            }
        }

        const { data, error } = await markTaskDone(taskId, user.id);
        if(data) {
            dispatch(setShowAlert({
                flag: true,
                alertType: "success",
                alertMessage: "Task Done!",
            }));
            dispatch(setToggle());
        }
    }

  return (
    <div className="modal fade" id="doneTaskModal" aria-hidden="true" aria-labelledby="inputTaskPriorityModalLabel2" tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content" >
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalToggleLabel2">Done Task!</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <p className='m-0 p-3'>This process <b>cannot be revert.</b> Are you sure that you want to mark the task as done?</p>
                    <div className="modal-footer">
                        <button 
                            onClick={handleDoneTask} 
                            className="btn btn-primary" 
                            data-bs-target="#doneTaskModal"
                            data-bs-toggle="modal"
                        >Yes</button>
                    </div>
                </div>
            </div>
        </div>
  )
}

export default DoneTaskModal