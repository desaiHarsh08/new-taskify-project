import React from 'react'

import { Link } from "react-router-dom";

import { GoWorkflow } from "react-icons/go";
import { IoSettingsOutline } from "react-icons/io5";
import { RiHomeOfficeLine } from "react-icons/ri";
import { RiLogoutBoxLine } from "react-icons/ri";

const Sidebar = () => {
    const sideNavArr = [
        // { path: "/home", label: "History", icon: <GoWorkflow /> },
        { path: "/home", label: "Home", icon: <RiHomeOfficeLine /> },
        { path: "tasks", label: "All Task", icon: <GoWorkflow /> },
        { path: "settings", label: "Settings", icon: <IoSettingsOutline /> },
        { path: "/", label: "Logout", icon: <RiLogoutBoxLine /> },
    ];

    const content = (
        <aside className="d-flex h-100 flex-column flex-shrink-0 p-3 text-bg-dark" style={{ width: "280px" }}>
            <a href="/" className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                <svg className="bi pe-none me-2" width="40" height="32"><use xlinkHref="#bootstrap"></use></svg>
                <span className="fs-4">Sidebar</span>
            </a>
            <hr />
            <ul className="nav nav-pills flex-column mb-auto ">
                {sideNavArr.map((link) => (
                    <li className="nav-item d-flex px-5">
                        <Link to={link.path} className="nav-link text-light acti ve d-flex gap-2 align-items-center" aria-current="page">
                            {link.icon}
                            {link.label}
                        </Link>
                    </li>
                ))}
            </ul>

            <hr />
            <div className="dropdown">
                <a href="#" className="d-flex align-items-center text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="https://github.com/mdo.png" alt="" width="32" height="32" className="rounded-circle me-2" />
                    <strong>mdo</strong>
                </a>
                <ul className="dropdown-menu dropdown-menu-dark text-small shadow">
                    <li><a className="dropdown-item" href="#">New project...</a></li>
                    <li><a className="dropdown-item" href="#">Settings</a></li>
                    <li><a className="dropdown-item" href="#">Profile</a></li>
                    <li><hr className="dropdown-divider" /></li>
                    <li><a className="dropdown-item" href="#">Sign out</a></li>
                </ul>
            </div>
        </aside>
    )

    return content
}

export default Sidebar