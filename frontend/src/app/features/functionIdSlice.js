import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    functionId: -1,
}

export const functionIdSlice = createSlice({
    name: 'functionId',
    initialState,
    reducers: {
        setFunctionId: (state, action) => {
            state.functionId = action.payload;
        },
    },
})

// Action creators are generated for each case reducer function
export const { setFunctionId } = functionIdSlice.actions

export const selectFunctionId = (state) => state.functionId.functionId;

export default functionIdSlice.reducer