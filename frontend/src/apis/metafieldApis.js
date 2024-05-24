import { API, handleApiError } from "../utils/api";

export const updateMetafield = async (metafield) => {
    try {
        const response = await API.put(`/api/v1/meta-fields/${metafield.id}`, metafield, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const addDefaultMetafield = async (metafield, functionMetadataId) => {
    try {
        const response = await API.post(`/api/v1/meta-fields/create-default`, {
            functionMetadataModelId: functionMetadataId,
            metaFieldDto: metafield
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}