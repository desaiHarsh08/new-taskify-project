import { API, handleApiError } from "../utils/api";

export const markFunctionMetadataDone = async (functionMetadataId, finishedMetadataByUserId, finishedDate) => {
    try {
        const response = await API.post(`/api/v1/function-metadatas/mark-done/${functionMetadataId}`, {finishedMetadataByUserId, finishedDate}, {
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

export const addMetadataDefault = async (functionMetadataDto) => {
    console.log("Creating functionMetadataDto:", functionMetadataDto)
    try {
        const response = await API.post(`/api/v1/function-metadatas/create-default`, functionMetadataDto, {
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

export const forwardMetadataFunctionAndTask = async (assignedUserId, functionMetadataModelId) => {
    console.log("forwarding:", {assignedUserId, functionMetadataModelId})
    try {
        const response = await API.post(`/api/v1/function-metadatas/forward-metadata`, {assignedUserId, functionMetadataModelId}, {
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