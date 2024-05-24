import { API, handleApiError } from "../utils/api";

export const getAllFunctionTemplates = async () => {
    try {
        const response = await API.get('/api/v1/function-templates');
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const getAllFunctionTemplatesByTaskTemplateId = async (taskTemplateId) => {
    try {
        const response = await API.get(`/api/v1/function-templates/task-template/${taskTemplateId}`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}