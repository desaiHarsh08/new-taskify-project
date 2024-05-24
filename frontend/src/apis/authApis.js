import { API, handleApiError } from "../utils/api";

export const doLogin = async ({email, password}) => {
    try {
        const response = await API.post('/auth/login', {email, password}, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const fetchUserByEmail = async (email) => {
    try {
        const response = await API.get(`/api/v1/users/email/${email}`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const fetchUserById = async (userId) => {
    console.log(userId);
    try {
        const response = await API.get(`/api/v1/users/${userId}`);
        return { error: null, data: response.data }
    } catch (error) {
        console.log(error);
        return handleApiError(error);
    }
}

export const doLoginByGoogle = async (email, userProfileImage) => {
    console.log(email, userProfileImage);
    try {
        const response = await API.post('/api/v1/users/google-login ', {email, userProfileImage}, {
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

export const fetchAllUsers = async () => {
    try {
        const response = await API.get('/api/v1/users', {
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