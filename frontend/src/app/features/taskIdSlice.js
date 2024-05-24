import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    taskId: -1,
}

export const taskIdSlice = createSlice({
    name: 'taskId',
    initialState,
    reducers: {
        setTaskId: (state, action) => {
            state.taskId = action.payload;
        },
    },
})

// Action creators are generated for each case reducer function
export const { setTaskId } = taskIdSlice.actions

export const selectTaskId = (state) => state.taskId.taskId;

export default taskIdSlice.reducer