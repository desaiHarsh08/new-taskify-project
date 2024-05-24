import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    functionArr: [],
}

export const functionArrSlice = createSlice({
    name: 'functionArr',
    initialState,
    reducers: {
        setFunctionArr: (state, action) => {
            console.log("Setting functionArr:", action.payload);
            state.functionArr = action.payload
        },
    },
})

// Action creators are generated for each case reducer function
export const { setFunctionArr } = functionArrSlice.actions

export const selectFunctionArr = (state) => state.functionArr.functionArr;

export default functionArrSlice.reducer