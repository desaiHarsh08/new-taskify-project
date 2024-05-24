import React from "react"
import Navbar from "../components/common/Navbar"
import Sidebar from "../components/common/Sidebar"
import { Outlet } from "react-router-dom"
import Alert from "../components/common/Alert"
import { useDispatch, useSelector } from "react-redux"
import { selectShowAlert } from "../app/features/showAlertSlice"

const Home = () => {
    const dispatch = useDispatch();

    const showAlert = useSelector(selectShowAlert);

    return (
        <>
            <main className="vh-100 vw-100 d-flex">
                {showAlert?.flag && <Alert alertType={showAlert?.alertType} alertMessage={showAlert?.alertMessage} />}
                <section 
                    id="sidebar-wrapper" 
                    className={`h-100 ${window.innerWidth < 992 ? 'offcanvas offcanvas-start': ''}`}
                    tabindex={window.innerWidth < 992 && -1}
                    aria-labelledby="sidebar-wrapper"
                >
                    <Sidebar />
                </section>
                <section id="main-screen" className="h-100">
                    <Navbar />
                    <div id="shared-area" className="p-3">
                        <Outlet />
                    </div>
                </section>
            </main>
        </>
    )
}

export default Home