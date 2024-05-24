import { API, handleApiError } from "../utils/api";

export const fetchTodaysLogs = async () => {
    try {
        const response = await API.get('/api/v1/logs');
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}