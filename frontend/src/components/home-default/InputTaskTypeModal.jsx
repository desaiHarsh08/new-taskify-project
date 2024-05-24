import React from 'react'

const InputTaskTypeModal = ({ selectedTaskType, setSelectedTaskType, taskArr }) => {
    console.log("taskArr:", taskArr);

    return (
        <div className="modal fade" id="inputTaskTypeModal" aria-hidden="true" aria-labelledby="inputTaskTypeModalLabel" tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalToggleLabel">Task Type</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <p className='mb-3'>Select a task type</p>
                        <select
                        value={taskArr.findIndex((ele, index) => ele.id == selectedTaskType?.id )}
                            onChange={(e) => {
                                const selectedIndex = e.target.selectedIndex;
                                console.log("setting:", selectedIndex,  taskArr[selectedIndex]);
                                setSelectedTaskType(taskArr[selectedIndex]);
                            }}
                            className="form-select"
                            aria-label="Default select example"
                        >
                            {taskArr.map((task, index) => {
                                if(index === 0) {
                                    return <option key={index} value={index}>Repair Service</option>
                                }else {
                                    return <option key={index} value={index}>New Enquiry</option>
                                }
                                
                            })}
                        </select>
                    </div>
                    <div className="modal-footer">
                        <button className="btn btn-primary" data-bs-target="#inputTaskPriorityModal" data-bs-toggle="modal">Continue</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default InputTaskTypeModal