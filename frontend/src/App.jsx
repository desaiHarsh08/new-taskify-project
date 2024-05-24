import React from 'react'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Root from './pages/Root';
import Home from './pages/Home';
import HomeDefault from './pages/HomeDefault';
import Settings from './pages/Settings';
import Tasks from './pages/Tasks';
import TasksDefault from './pages/TasksDefault';
import TaskView from './pages/TaskView';
import TaskMetadata from './pages/TaskMetadata';
import TaskViewDefault from './pages/TaskViewDefault';

const App = () => {
    const router = createBrowserRouter([
        { path: "/", element: <Root /> },
        {
            path: "/home",
            element: <Home />,
            children: [
                { path: "", element: <HomeDefault />},
                { path: "settings", element: <Settings />},
                { 
                    path: "tasks", 
                    element: <Tasks />,
                    children: [
                        { path: "", element: <TasksDefault />},
                        {
                            path: ":taskId", 
                            element: <TaskView />,
                            children: [
                                { path: "", element: <TaskViewDefault /> },
                                { path: ":functionId", element: <TaskMetadata /> }
                            ]
                        }
                    ]
                },
            ]
        },
    ]);
    return (
        <RouterProvider router={router} />
    )
}

export default App