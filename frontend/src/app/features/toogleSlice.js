import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    flag: false,
}

export const toggleSlice = createSlice({
    name: 'toggle',
    initialState,
    reducers: {
        setToggle: (state, action) => {
            console.log("Setting toggle:", action.payload);
            state.flag = !state.flag;
        },
    },
})

// Action creators are generated for each case reducer function
export const { setToggle } = toggleSlice.actions

export const selectToggle = (state) => state.toggle.flag;

export default toggleSlice.reducer