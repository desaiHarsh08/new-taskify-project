import React, { useEffect, useState } from 'react'
import { fetchAllUsers } from '../../apis/authApis';
import { selectUser } from '../../app/features/userSlice';
import { useDispatch, useSelector } from 'react-redux';
import { forwardMetadataFunctionAndTask } from '../../apis/functionMetadataApis';
import { selectIsForwarded, setIsForwarded } from '../../app/features/isForwardedSlice';
import { setShowAlert } from '../../app/features/showAlertSlice';
import { setToggle } from '../../app/features/toogleSlice';

const ForwardModal = ({ functionMetadataModelId }) => {
    const dispatch = useDispatch();

    const user = useSelector(selectUser);

    const isForwarded = useSelector(selectIsForwarded);

    const [userArr, setUserArr] = useState([]);
    const [assignedUser, setAssignedUser] = useState();
    const [mySelectUserIndex, setMySelectUserIndex] = useState(0);

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchAllUsers();
            if(data) {
                setUserArr(data.payload);
                setAssignedUser(data.payload[0]);
            }
        })();
    }, []);



    const handleForward = async () => {
        const { data, error } = await forwardMetadataFunctionAndTask(assignedUser.id, functionMetadataModelId);
        // TODO: RELOAD THE functionArr
        console.log(data) 
        dispatch(setIsForwarded(true));
        dispatch(setToggle());
        dispatch(setShowAlert({flag: true, alertType: "success", alertMessage: "Function forwarded!"}));
    }

    const handleUserChange = (e) => {
        const user = userArr.find((ele, index) => index === e.target.selectedIndex);
        setAssignedUser(user);
        setMySelectUserIndex(e.target.selectedIndex);
    }


    return (
        <div
            className="modal fade"
            id="forwardMetadataModal"
            aria-hidden="true"
            aria-labelledby="inputTaskTypeModalLabel"
            tabIndex="-1"
        >
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalToggleLabel">Forward</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <select value={mySelectUserIndex} onChange={(e)=>handleUserChange(e)} className="form-select" aria-label="Default select example">
                            {userArr.map((ele, index) => {
                                return <option value={index}>{ele.email}</option>

                            })}
                        </select>
                    </div>
                    <div className="modal-footer">
                        <button 
                            className="btn btn-primary" 
                            data-bs-target="#forwardMetadataModal" 
                            data-bs-toggle="modal"
                            onClick={handleForward}
                        >Continue</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ForwardModal