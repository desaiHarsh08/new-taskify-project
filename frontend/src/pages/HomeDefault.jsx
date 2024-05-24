import React, { useEffect, useState } from "react"
import TaskStats from "../components/home-default/TaskStats"
import ActivityLogs from "../components/home-default/ActivityLogs"

import HomeAction from "../components/home-default/HomeAction";
import { fetchAllTasks } from "../apis/taskApis";
import { useSelector } from "react-redux";
import { selectToggle } from "../app/features/toogleSlice";

const HomeDefault = () => {
    const toggle = useSelector(selectToggle);
    const [allTasks, setAllTasks] = useState([]);

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchAllTasks();
            if(data) {
                setAllTasks(data.payload);
            } 
        })();
    }, [toggle]);

    return (
        <div id="home-default" className="d-flex gap-2 h-100">
            <section className={`${window.innerWidth > 991 ? 'w-75' : 'w-100'}`}>
                <HomeAction allTasks={allTasks} />
                {window.innerWidth > 991 && <ActivityLogs />}
            </section>
            <section className={`${window.innerWidth > 991 ? 'w-25 h-100 ' : 'w-100'} `}>
                {allTasks && <TaskStats allTasks={allTasks} />}
                {window.innerWidth < 992 && <ActivityLogs />}
            </section>
        </div>
    )
}

export default HomeDefault