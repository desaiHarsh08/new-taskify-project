import React, { useEffect, useState } from 'react'
import { fetchAllUsers } from '../apis/authApis';

const Settings = () => {
    const [userArr, setUserArr] = useState([]);

    useEffect(() => {
        (async () => {
            const { data, error } = await fetchAllUsers();
            setUserArr(data.payload);
        })();
    }, []);

    return (
        <div className='w-100'>
            <h1>Users</h1>
            <div className='w-100 overflow-auto'>
                <table className="table table-secondary" style={{minWidth: '1128px'}}>
                    <thead className='w-100' style={{ backgroundColor: "#f8f8f8" }}>
                        <tr className='w-100'>
                            <th scope="col" className='text-center'>#</th>
                            <th scope="col" className='text-center'>Name</th>
                            <th scope="col" className='text-center'>Email</th>
                            <th scope="col" className='text-center'>Department</th>
                            <th scope="col" className='text-center'>Disabled</th>
                            <th scope="col" className='text-center'>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {userArr.map((user, index) => (
                            <tr key={`user-${index}`} className='table table-light'>
                                <th scope="row" className='text-center'>{user.id}</th>
                                <td className='text-center'>{user.name}</td>
                                <td className='text-center'>{user.email}</td>
                                <td className='text-center'>
                                    <p className="badge text-bg-secondary text-center">{user.department}</p>
                                </td>
                                <td className='text-center'>
                                    <p className="badge text-bg-secondary text-center ">{user.disabled ? "YES" : "NO"}</p>
                                </td>
                                <td className='text-center'>
                                    <button type="button" className="btn btn-sm btn-danger px-4">Edit</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Settings