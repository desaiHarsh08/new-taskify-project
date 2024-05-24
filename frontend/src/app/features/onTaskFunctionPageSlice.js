import { createSlice } from "@reduxjs/toolkit";

export const onTaskFunctionPageSlice = createSlice({
    name: 'onTaskFunctionPage',
    initialState: { onTaskFunctionPage: false },
    reducers: {
        setOnTaskFunctionPage: (state, action) => {
            state.onTaskFunctionPage = action.payload;
        },
    },
})

// Action creators are generated for each case reducer function
export const { setOnTaskFunctionPage } = onTaskFunctionPageSlice.actions

export const selectOnTaskFunctionPage = (state) => state.onTaskFunctionPage.onTaskFunctionPage;

export default onTaskFunctionPageSlice.reducer