import React, { useEffect, useState } from 'react'

const TaskInfoDetailsModal = ({ selectedTaskType, taskArr, taskInfo, setTaskInfo }) => {

    useEffect(() => { }, [selectedTaskType]);

    const handleTaskInfoChange = (e) => {
        const { name, value } = e.target;

        setTaskInfo(prev => ({ ...prev, [name]: value }));
    }

    return (
        <div className="modal fade" id="taskInfoDetailsModal" aria-hidden="true" aria-labelledby="taskInfoDetailsModal" tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="taskInfoDetailsModalToggleLabel2">Task Info: {selectedTaskType.functionTemplateDtoList.length > 12 ? "New Pump Enquiry" : "Repair Service"}</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <form className='d-flex flex-column gap-2'>
                            <div class="form-group">
                                <label for="pumpType">Pump Type</label>
                                <input
                                    type="text"
                                    class="form-control"
                                    id="pumpType"
                                    value={taskInfo.pumpType}
                                    onChange={handleTaskInfoChange}
                                    name='pumpType'
                                />
                            </div>
                            {selectedTaskType.taskType.toUpperCase() === "NEW PUMP INQUIRY" ?
                                <>
                                    <div class="form-group">
                                        <label for="pumpManufacturer">Pump Manufaturer</label>
                                        <input
                                            type="text"
                                            class="form-control"
                                            value={taskInfo.pumpManufacturer}
                                            onChange={handleTaskInfoChange}
                                            name='pumpManufacturer'
                                            id="pumpManufacturer"
                                        />
                                    </div>
                                    <div class="form-group">
                                        <label for="specification">Specification</label>
                                        <input
                                            type="text"
                                            class="form-control"
                                            value={taskInfo.specification}
                                            onChange={handleTaskInfoChange}
                                            name='specification'
                                            id="specification"
                                        />
                                    </div>
                                </>
                                :
                                <div class="form-group">
                                    <label for="problemDescription">Problem being faced</label>
                                    <textarea
                                        class="form-control"
                                        id="problemDescription"
                                        value={taskInfo.problemDescription}
                                        onChange={handleTaskInfoChange}
                                        name='problemDescription'
                                    />
                                </div>

                            }
                        </form>

                    </div>
                    <div className="modal-footer">
                        <button className="btn btn-primary" data-bs-target="#customerInfoModal" data-bs-toggle="modal">Back</button>
                        <button className="btn btn-primary" data-bs-target="#assignTaskModal" data-bs-toggle="modal">Continue</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default TaskInfoDetailsModal