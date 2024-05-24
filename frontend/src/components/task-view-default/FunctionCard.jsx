import React from 'react'
import { useNavigate } from 'react-router-dom'

const FunctionCard = ({ func }) => {
    const navigate = useNavigate();

    const handleFunctionClick = (functionId) => {
        navigate(`${functionId}`, { replace: true });
    }

    return (
        <div onClick={() => handleFunctionClick(func.id)} className="card m-2" style={{ fontSize: "14px", cursor: "pointer" }}>
            <h5 className="card-header" style={func.functionDone ? { backgroundColor: "rgb(182 227 182)" } : { backgroundColor: "#d3d3d3" }}>{func.functionTitle}</h5>
            <div className="card-body">
                <p className="card-text">{func.functionDescription}</p>
            </div>
            <div className="card-footer">
                <span className='text-bg-secondary p-1 rounded-1' style={{ fontSize: "11px" }}>{func.functionDepartment}</span>
            </div>
        </div>
    )
}

export default FunctionCard