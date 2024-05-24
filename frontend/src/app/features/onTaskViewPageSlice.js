import { createSlice } from "@reduxjs/toolkit";

export const onTaskViewPageSlice = createSlice({
    name: 'onTaskViewPage',
    initialState: { onTaskViewPage: false },
    reducers: {
        setOnTaskViewPage: (state, action) => {
            state.onTaskViewPage = action.payload;
        },
    },
})

// Action creators are generated for each case reducer function
export const { setOnTaskViewPage } = onTaskViewPageSlice.actions

export const selectOnTaskViewPage = (state) => state.onTaskViewPage.onTaskViewPage;

export default onTaskViewPageSlice.reducer