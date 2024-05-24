import { API, handleApiError } from "../utils/api";

export const getAllFunctions = async () => {
    try {
        const response = await API.get('/api/v1/functions');
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const getFunctionsByTaskId = async (taskId) => {
    try {
        const response = await API.get(`/api/v1/functions/by-task/${taskId}`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const addFunction = async (functionDto, functionTemplateDto) => {
    console.log("Adding new function:", {functionDto, functionTemplateDto});
    try {
        const response = await API.post(
            '/api/v1/functions/create', 
            {functionDto, functionTemplateDto}
        );
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const addDefaultFunction = async (functionDto, functionTemplateDto) => {
    console.log("Adding new function:", {functionDto, functionTemplateDto});
    try {
        const response = await API.post(
            '/api/v1/functions/create-default', 
            {functionDto, functionTemplateDto}
        );
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const deleteFunction = async (functionId) => {
    console.log(functionId);
    try {
        const response = await API.delete(`/api/v1/functions/${functionId}`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}