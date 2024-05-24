import { configureStore } from '@reduxjs/toolkit'
import authReducer from './features/authSlice'
import functionArrReducer from './features/functionArrSlice'
import userReducer from './features/userSlice'
import onTaskDefaultPageReducer from './features/onTaskDefaultPageSlice'
import onTaskViewPageReducer from './features/onTaskViewPageSlice'
import onTaskFunctionPageReducer from './features/onTaskFunctionPageSlice'
import taskIdReducer from './features/taskIdSlice'
import functionIdReducer from './features/functionIdSlice'
import isForwardedReducer from './features/isForwardedSlice'
import showAlertReducer from './features/showAlertSlice'
import toggleReducer from './features/toogleSlice'

export const store = configureStore({
    reducer: {
        auth: authReducer,
        functionArr: functionArrReducer,
        user: userReducer,
        onTaskDefaultPage: onTaskDefaultPageReducer,
        onTaskViewPage: onTaskViewPageReducer,
        onTaskFunctionPage: onTaskFunctionPageReducer,
        taskId: taskIdReducer,
        functionId: functionIdReducer,
        isForwarded: isForwardedReducer,
        showAlert: showAlertReducer,
        toggle: toggleReducer,
    },
})