import React, { useEffect, useState } from "react"
import { fetchTodaysLogs } from "../../apis/logsApi";
import { getFormattedDate } from "../../utils/helper";
import { useSelector } from "react-redux";
import { selectToggle } from "../../app/features/toogleSlice";

const ActivityLogs = () => {
    const toggle = useSelector(selectToggle);

    const [logArr, setLogArr] = useState([]);

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchTodaysLogs();
            console.log("logs:", data.payload);
            const tmpArr = [];
            for(let i = data.payload.length - 1; i >= 0; i--) {
                tmpArr.push(data.payload[i]);
            }
            setLogArr(tmpArr);
        })();
    }, [toggle]);

    return (
        <div id="activity-container" className="py-3">
            <h2 className="border-bottom py-3">Activity Logs</h2>
            <div className="" style={{overflow: "auto", height: "99%"}}>
            {logArr.length === 0 && 'No logs available'}
                {logArr.length > 0 && <table className="table" style={{fontSize: "12px"}}>
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Time</th>
                            <th scope="col">Type</th>
                            <th scope="col">Action</th>
                            <th scope="col">By</th>
                        </tr>
                    </thead>
                    <tbody>
                        {logArr.map((log, index) => (
                            <tr>
                                <th scope="row">{index + 1}</th>
                                <td>{getFormattedDate(log?.date)}</td>
                                <td>{log.type}</td>
                                <td>{log.action}</td>
                                <td>{log.message}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>}
            </div>
        </div>
    )
}

export default ActivityLogs