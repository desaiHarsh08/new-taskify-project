import { createSlice } from "@reduxjs/toolkit";

export const onTaskDefaultPageSlice = createSlice({
    name: 'onTaskDefaultPage',
    initialState: { onTaskDefaultPage: true},
    reducers: {
        setOnTaskDefaultPage: (state, action) => {
            state.onTaskDefaultPage = action.payload;
        },
    },
})

// Action creators are generated for each case reducer function
export const { setOnTaskDefaultPage } = onTaskDefaultPageSlice.actions

export const selectOnTaskDefaultPage = (state) => state.onTaskDefaultPage.onTaskDefaultPage;

export default onTaskDefaultPageSlice.reducer