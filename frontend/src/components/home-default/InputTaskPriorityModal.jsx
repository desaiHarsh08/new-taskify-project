import React from 'react'

const InputTaskPriorityModal = ({ taskPriority, setTaskPriority }) => {
    return (
        <div className="modal fade" id="inputTaskPriorityModal" aria-hidden="true" aria-labelledby="inputTaskPriorityModalLabel2" tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalToggleLabel2">Task Priority</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <p className='mb-3'>Select the task priority</p>
                        <select
                            value={taskPriority}
                            onChange={(e) => {
                                setTaskPriority(e.target.value);
                            }}
                            className="form-select"
                            aria-label="Default select example"
                        >
                            <option value="NORMAL">NORMAL</option>
                            <option value="INTERMEDIATE">INTERMEDIATE</option>
                            <option value="HIGH">HIGH</option>
                        </select>
                    </div>
                    <div className="modal-footer">
                        <button className="btn btn-primary" data-bs-target="#inputTaskTypeModal" data-bs-toggle="modal">Back</button>
                        <button className="btn btn-primary" data-bs-target="#assignTaskModal" data-bs-toggle="modal">Continue</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default InputTaskPriorityModal