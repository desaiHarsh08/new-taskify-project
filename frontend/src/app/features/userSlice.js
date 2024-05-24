import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    user: localStorage.getItem('user')===undefined ? null : JSON.parse(localStorage.getItem('user')),
}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setUser: (state, action) => {
            console.log("action.payload:", action.payload)
            state.user = action.payload;
            localStorage.setItem("user", JSON.stringify(action.payload));
        },
    },
})

// Action creators are generated for each case reducer function
export const { setUser } = userSlice.actions

export const selectUser = (state) => state.user.user;

export default userSlice.reducer