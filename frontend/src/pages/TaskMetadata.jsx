import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'
import { setOnTaskDefaultPage } from '../app/features/onTaskDefaultPageSlice';
import { setOnTaskViewPage } from '../app/features/onTaskViewPageSlice';
import { setOnTaskFunctionPage } from '../app/features/onTaskFunctionPageSlice';
import { setFunctionId } from '../app/features/functionIdSlice';
import { setTaskId } from '../app/features/taskIdSlice';
import { getFunctionsByTaskId } from '../apis/functionApis';
import { updateMetafield } from '../apis/metafieldApis';
import { markFunctionMetadataDone } from '../apis/functionMetadataApis';
import { selectUser } from '../app/features/userSlice';
import ForwardModal from '../components/task-metadata/ForwardModal';
import { selectIsForwarded } from '../app/features/isForwardedSlice';
import { setShowAlert } from '../app/features/showAlertSlice';
import { selectToggle, setToggle } from '../app/features/toogleSlice';

const TaskMetadata = () => {
    const dispatch = useDispatch();

    const user = useSelector(selectUser);
    const isForwarded = useSelector(selectIsForwarded);
    const toggle = useSelector(selectToggle);

    const { taskId, functionId } = useParams();

    const [functionObj, setFunctionObj] = useState();

    const [activeMetadataIndex, setActiveMetadataIndex] = useState(0);

    useEffect(() => {
        fetchFunctionObj();

        dispatch(setOnTaskDefaultPage(true));
        dispatch(setOnTaskViewPage(true));
        dispatch(setOnTaskFunctionPage(true));
        dispatch(setFunctionId(functionId));
        dispatch(setTaskId(taskId));
    }, [isForwarded, toggle]);

    const fetchFunctionObj = async () => {
        const { data, error } = await getFunctionsByTaskId(taskId);
        if (data) {
            console.log("F:", data.payload);
            const tmpFunction = data.payload.find((ele) => ele.id == functionId);
            console.log(tmpFunction)
            setFunctionObj(tmpFunction);

            for (let i = 0; i < data.payload.length; i++) {
                for(let j = 0; j < data.payload[i].functionMetadataDtoList.length; j++) {
                    if (data.payload[i].functionMetadataDtoList[j].functionMetadataDone === false) {
                        setActiveMetadataIndex(j);
                        return;
                    }
                }
            }

        }
    }

    const handleMetafieldSave = async (e, metadata) => {
        if (metadata.functionMetadataDone) {
            return;
        }
        console.log(metadata)
        for (let i = 0; i < metadata.metaFieldModelList.length; i++) {
            let metafield = metadata.metaFieldModelList[i];
            const { data, error } = await updateMetafield(metafield);
            console.log("Updated metafield:", data);
        }

        dispatch(setShowAlert({flag: true, alertType: "success", alertMessage: "Data Saved!"}));
    }

    const handleDoneMetadata = async (metdataId) => {
        const { data, error } = await markFunctionMetadataDone(metdataId, user?.id);
        console.log("data:", data);
        fetchFunctionObj();
        dispatch(setToggle());
        dispatch(setShowAlert({flag: true, alertType: "success", alertMessage: "Marked as done!"}));
    }

    return (
        <div className=' w-100 overflow-y-auto' style={{ overflowY: "scroll", height: "90%" }}>
            <div className='my-3'>
                <h3>{functionObj?.functionTitle}</h3>
            </div>

            <div>
                <div className="accordion" id="accordionExample">
                    {functionObj?.functionMetadataDtoList.map((metadata, metadataIndex) => {
                        if (metadata.functionMetadataDone || activeMetadataIndex === metadataIndex) {
                            return (
                                <div className="accordion-item my-2">
                                    <h2 className="accordion-header">
                                        <button
                                            className="accordion-button"
                                            type="button"
                                            data-bs-toggle="collapse"
                                            data-bs-target={`#collapse${metadataIndex}`}
                                            aria-expanded="true" aria-controls={`collapse${metadataIndex}`}
                                        >
                                            #{metadataIndex + 1} {metadata?.metadataTitle}
                                        </button>
                                    </h2>
                                    <div
                                        id={`collapse${metadataIndex}`}
                                        className={`accordion-collapse collapse ${activeMetadataIndex === metadataIndex ? "show" : ""} `}
                                        data-bs-parent="#accordionExample"
                                    >
                                        <div className="accordion-body" style={{ maxHeight: "345px", overflow: "auto" }}>
                                            <div>
                                                {metadata.metaFieldModelList.map((metafield, metafieldIndex) => (
                                                    <div className="mb-3">
                                                        <label for="exampleInputEmail1" className="form-label">{metafield.fieldName}</label>
                                                        <input
                                                            type={`${metafield.fieldName.toLowerCase().includes("date") ? "date" : "text"}`}
                                                            className="form-control" id="exampleInputEmail1"
                                                            value={metafield.fieldValue}
                                                        />
                                                    </div>
                                                ))}
                                                {console.log("checking condition:", metadata.functionMetadataDone && metadata.metadataAssignedToUserId === user.id)}
                                                {console.log("user:", user)}
                                                {console.log(`metadata.functionMetadataDone: ${metadata.functionMetadataDone}, metadata.metadataAssignedToUserId === user.id: ${metadata.metadataAssignedToUserId === user.id}`)}
                                                {(!metadata.functionMetadataDone && metadata.metadataAssignedToUserId === user.id) && (
                                                    <>

                                                        <button
                                                            type="button"
                                                            onClick={(e) => handleMetafieldSave(e, metadata)}
                                                            className='btn btn-sm btn-primary'
                                                        >Save</button>
                                                        <ForwardModal functionMetadataModelId={metadata.id} />
                                                        <button
                                                            className='btn btn-sm btn-danger mx-2'
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#forwardMetadataModal"
                                                        >Forward</button>
                                                        <button
                                                            type="button"
                                                            className='btn  btn-sm  btn-success '
                                                            onClick={(e) => handleDoneMetadata(metadata.id)}
                                                        >Done</button>
                                                    </>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            )
                        }
                    })}
                </div>
            </div>
        </div>
    )
}

export default TaskMetadata