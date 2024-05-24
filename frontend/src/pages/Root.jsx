import React, { useState } from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { doLogin, fetchUserByEmail } from '../apis/authApis';
import { setToken } from '../app/features/authSlice';
import { jwtDecode } from 'jwt-decode';
import { setUser } from '../app/features/userSlice';

const Root = () => {
	const dispatch = useDispatch();

    const navigate = useNavigate();

    const [credentials, setCredentials] = useState({ email: "", password: "" });

    const handleCredentialChange = (e) => {
        const { name, value } = e.target;
        setCredentials((prev) => ({ ...prev, [name]: value }));
    }

    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        console.log(credentials)
        const { data, error } = await doLogin(credentials);
        console.log(data)
        if (error !== null) {
            alert("Invalid credentials!");
            return;
        }
        dispatch(setToken(data.payload));

		const { data: userResponse, error: userError } = await fetchUserByEmail(jwtDecode(data.payload.token).sub);
		console.log(userResponse.payload);
		dispatch(setUser(userResponse.payload));

        navigate("/home", { replace: true });

    }

	return (
		<main id='login-page' className="form-signin w-100 m-auto d-flex justify-content-center align-items-center vh-100 bg-tertiary">
			<form onSubmit={handleLoginSubmit} className={`${window.innerWidth > 640 ? 'w-25 ': 'w-75'}`}>
				<img className="mb-4" src="/taskify-logo.png" alt="" width="57" height="57" />
				<h1 className="h3 mb-3 fw-normal text-light">Please sign in</h1>
				<div className="form-floating mb-3">
					<input 
						type="email" 
						className="form-control" 
						id="floatingInput" 
						placeholder="name@example.com" 
						name='email'
						value={credentials.email}
						onChange={handleCredentialChange}
					/>
					<label for="floatingInput">Email address</label>
				</div>
				<div className="form-floating">
					<input 
						type="password" 
						className="form-control" 
						id="floatingPassword" 
						placeholder="Password" 
						name='password'
						value={credentials.password}
						onChange={handleCredentialChange}
					/>
					<label for="floatingPassword">Password</label>
				</div>
				<div className='d-flex justify-content-start my-3'>
					<button className="btn btn-primary w-100 py-2" type="submit">Sign in</button>
				</div>
			</form>
		</main>
	)
}


export default Root