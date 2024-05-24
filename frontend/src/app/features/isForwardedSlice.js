import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    flag: false,
}

export const isForwardedSlice = createSlice({
    name: 'isForwarded',
    initialState,
    reducers: {
        setIsForwarded: (state, action) => {
            console.log("Setting isForwarded:", action.payload);
            state.flag = action.payload
        },
    },
})

// Action creators are generated for each case reducer function
export const { setIsForwarded } = isForwardedSlice.actions

export const selectIsForwarded = (state) => state.isForwarded.flag;

export default isForwardedSlice.reducer