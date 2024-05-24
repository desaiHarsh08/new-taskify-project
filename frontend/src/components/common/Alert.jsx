import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { selectShowAlert, setShowAlert } from '../../app/features/showAlertSlice';

const Alert = ({ alertType = "success", alertMessage = "This a default message" }) => {
    const dispatch = useDispatch();

    const showAlert = useSelector(selectShowAlert);

    return (
        <div className='position-absolute bottom-0 d-flex justify-content-center' style={{width: "100%"}}>
            <div className={`alert alert-${alertType} alert-dismissible fade show`} style={{zIndex: "9999999"}} role="alert">
                {alertMessage}
                <button 
                    type="button" 
                    onClick={() => dispatch(setShowAlert({...showAlert, flag: false}))} 
                    className="btn-close" data-bs-dismiss="alert" 
                    aria-label="Close"
                ></button>
            </div>
        </div>
    )
}

export default Alert