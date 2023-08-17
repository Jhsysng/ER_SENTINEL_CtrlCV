import React, {useContext, useEffect, useState} from 'react';
import {parseJwt} from "../utils/JwtUtils";
import {useCookies} from "react-cookie";
import AuthContext from "./AuthContext";

const LoginCallBackPage = () => {

    const Auth = useContext(AuthContext);

    const [cookie, setCookie] = useCookies(['refreshToken']);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [isLoggedIn, setIsLoggedIn] = useState(false);


    let accessToken, refreshToken;
    useEffect(() => {
        const isLoggedIn = Auth.userIsAuthenticated();
        setIsLoggedIn(isLoggedIn);

        accessToken = new URL(window.location.href).searchParams.get("accessToken")
        refreshToken = new URL(window.location.href).searchParams.get("refreshToken")

        const data = parseJwt(accessToken);

        // local storage 에 data, access token 저장
        const user = { data, accessToken };

        setCookie('refreshToken', refreshToken, {path: '/'});

        Auth.userLogin(user);

        setIsLoggedIn(true);

        console.log(data)

        window.location.href = "/";
    }, []);

    return (
        <div>
            Login Callback Page...

        </div>
    );
};

export default LoginCallBackPage;
