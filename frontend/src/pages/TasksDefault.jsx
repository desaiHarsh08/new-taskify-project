import React, { useEffect, useState } from 'react'
import FilterTabs from '../components/tasks-default/FilterTabs';
import { fetchAllTaskTemplates } from '../apis/taskTemplatesApis';
import { fetchAllTasks } from '../apis/taskApis';
import { getFormattedDate } from '../utils/helper';
import TaskRow from '../components/tasks-default/TaskRow';
import { useDispatch } from 'react-redux';
import { setOnTaskDefaultPage } from '../app/features/onTaskDefaultPageSlice';
import { setOnTaskViewPage } from '../app/features/onTaskViewPageSlice';
import { setOnTaskFunctionPage } from '../app/features/onTaskFunctionPageSlice';

const TasksDefault = () => {
    const dispatch = useDispatch();

    const [selectedTab, setSelectedTab] = useState();
    const [taskTemplateArr, setTaskTemplateArr] = useState([]);
    const [allTask, setAllTask] = useState([]);
    const [taskArr, setTaskArr] = useState([]);
    const [tabArr, setTabArr] = useState([
        { tabLabel: "All Task", isSelected: true, totalTask: 0 },
        { tabLabel: "Completed", isSelected: false, totalTask: 0 },
        { tabLabel: "Pending", isSelected: false, totalTask: 0 },
        { tabLabel: "HIGH", isSelected: false, totalTask: 0 },
        { tabLabel: "INTERMEDIATE", isSelected: false, totalTask: 0 },
        { tabLabel: "NORMAL", isSelected: false, totalTask: 0 },
    ]);

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchAllTasks();
            // console.log("all task:", data.payload);
            if (data) {
                const tmpArr = [];
                for(let i = data.payload.length - 1; i >= 0; i--) {
                    tmpArr.push(data.payload[i]);
                }
                setAllTask(tmpArr);
                setTaskArr(tmpArr);

                let newTabArr = [...tabArr];
                for (let i = 0; i < newTabArr.length; i++) {
                    newTabArr[i] = filterTask(newTabArr[i], tmpArr);
                    console.log("from filter task: ", newTabArr[i]);
                }
                setTabArr(newTabArr);


                const { data: d1, error: e2 } = await fetchAllTaskTemplates();
                // console.log("tt:", d1.payload);
                setTaskTemplateArr(d1.payload);


            }
        })();

        dispatch(setOnTaskDefaultPage(true));
        dispatch(setOnTaskViewPage(false));
        dispatch(setOnTaskFunctionPage(false));
    }, []);


    const filterTask = (tab, allTask) => {
        // console.log(tab);
        if (tab.tabLabel === "All Task") {
            tab.totalTask = allTask.length;
        }
        else if (tab.tabLabel === "Completed") {
            let totalTask = allTask.filter((ele) => ele.taskCompleted === true);
            // console.log("Completed task:", totalTask);
            tab.totalTask = totalTask.length;
        }
        else if (tab.tabLabel === "Pending") {
            let totalTask = allTask.filter((ele) => ele.taskCompleted === false);
            tab.totalTask = totalTask.length;
        }
        else if (tab.tabLabel === "HIGH") {
            let totalTask = allTask.filter((ele) => ele.taskPriority === "HIGH");
            tab.totalTask = totalTask.length;
        }
        else if (tab.tabLabel === "INTERMEDIATE") {
            let totalTask = allTask.filter((ele) => ele.taskPriority === "INTERMEDIATE");
            tab.totalTask = totalTask.length;
        }
        else if (tab.tabLabel === "NORMAL") {
            let totalTask = allTask.filter((ele) => ele.taskPriority === "NORMAL");
            tab.totalTask = totalTask.length;
        }

        // console.log("return tab:", tab);
        return tab;
    }

    const handleTabClick = (tIndex) => {
        let newTabArr = [...tabArr];
        newTabArr = newTabArr.map((tab, index) => {
            if(index === tIndex) {
                tab.isSelected = true;
                if (tab.tabLabel === "All Task") {
                    tab.totalTask = allTask.length;
                    setTaskArr(allTask);
                }
                else if (tab.tabLabel === "Completed") {
                    let totalTask = allTask.filter((ele) => ele.taskCompleted === true);
                    // console.log("Completed task:", totalTask);
                    tab.totalTask = totalTask.length;
                    setTaskArr(totalTask);
                }
                else if (tab.tabLabel === "Pending") {
                    let totalTask = allTask.filter((ele) => ele.taskCompleted === false);
                    tab.totalTask = totalTask.length;
                    setTaskArr(totalTask);
                }
                else if (tab.tabLabel === "HIGH") {
                    let totalTask = allTask.filter((ele) => ele.taskPriority === "HIGH");
                    tab.totalTask = totalTask.length;
                    setTaskArr(totalTask);
                }
                else if (tab.tabLabel === "INTERMEDIATE") {
                    let totalTask = allTask.filter((ele) => ele.taskPriority === "INTERMEDIATE");
                    tab.totalTask = totalTask.length;
                    setTaskArr(totalTask);
                }
                else if (tab.tabLabel === "NORMAL") {
                    let totalTask = allTask.filter((ele) => ele.taskPriority === "NORMAL");
                    tab.totalTask = totalTask.length;
                    setTaskArr(totalTask);
                }
            }
            else {
                tab.isSelected = false;
            }
            return tab;
        });

        setTabArr(newTabArr);
    }



    return (
        <div>
            <div className='border-bottom'>
                <h1>All Tasks</h1>
                <p>View all of your task here</p>
            </div>
            <FilterTabs handleTabClick={handleTabClick} setSelectedTab={setSelectedTab} tabArr={tabArr} setTabArr={setTabArr} />
            <div id='list-task-container' className='overflow-scroll'>
                <div className='text-sm'>
                    <table className="table table-secondary w-100 text-sm" style={{minWidth: "1128px"}}>
                        <thead className='w-100' style={{ backgroundColor: "#f8f8f8" }}>
                            <tr className='w-100'>
                                <th scope="col" className='text-center'>Sr. No.</th>
                                <th scope="col" className='text-center'>Task Id</th>
                                <th scope="col" className='text-center'>Type</th>
                                <th scope="col" className='text-center'>Task Priority</th>
                                <th scope="col" className='text-center'>Created At</th>
                                <th scope="col" className='text-center'>Staus</th>
                                <th scope="col" className='text-center'>Finished At</th>
                                <th scope="col" className='text-center'>Action</th>
                            </tr>
                        </thead>
                        <tbody id='list-body' style={{ height: "12px", background: "red"}} >
                            {taskTemplateArr && taskArr.map((task, index) => (
                                <TaskRow index={index} task={task} taskTemplateArr={taskTemplateArr} />
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default TasksDefault