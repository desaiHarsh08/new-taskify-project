import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    showAlert: {
        flag: false,
        alertType: "success",
        alertMessage: "This is a default message!"
    },
}

export const showAlertSlice = createSlice({
    name: 'showAlert',
    initialState,
    reducers: {
        setShowAlert: (state, action) => {
            console.log(action.payload);
            state.showAlert = action.payload;
        },
    },
})

// Action creators are generated for each case reducer function
export const { setShowAlert } = showAlertSlice.actions

export const selectShowAlert = (state) => state.showAlert.showAlert;

export default showAlertSlice.reducer