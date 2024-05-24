import { API, handleApiError } from "../utils/api";

export const fetchAllTasks = async () => {
    try {
        const response = await API.get(`/api/v1/tasks`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const fetchTaskbyId = async (taskId) => {
    try {
        const response = await API.get(`/api/v1/tasks/${taskId}`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const markTaskDone = async (taskId, taskFinishedByUserId, finishedDate) => {
    try {
        const response = await API.post(`/api/v1/tasks/mark-done/${taskId}`, {
            taskFinishedByUserId, finishedDate
        });
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const addTask = async (task) => {
    console.log("Creating task:", {...task})
    try {
        const response = await API.post(`/api/v1/tasks/create`, task, {
            headers: {
                'Content-Type': "application/json",
            }
        });
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const forwardTask = async (taskId, department) => {
    try {
        const response = await API.post(`/api/v1/tasks/forward-task`, {taskId, department}, {
            headers: {
                'Content-Type': "application/json",
            }
        });
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}