import { API, handleApiError } from "../utils/api";

export const fetchAllTaskTemplates = async () => {
    try {
        const response = await API.get(`/api/v1/task-templates`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}