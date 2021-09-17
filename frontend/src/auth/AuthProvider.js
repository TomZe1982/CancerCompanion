import {useContext, useState} from "react";
import jwt from 'jsonwebtoken'
import {getToken, deleteUser as deleteAPI, updateUser as updateAPI, getNewVideo as getNewVideoApi, deleteVideo as deleteVideoApi} from "../service/apiService";
import AuthContext from "./AuthContext";

export default function AuthProvider ({children}) {

    const [token, setToken] = useState();

    const claims = jwt.decode(token)

    const user = claims && {
        userName : claims.sub,
        role : claims.role,
    }

    const login = (credentials) => getToken(credentials).then(setToken)

    const logout = () => setToken()

    const deleteUser = (userName) => deleteAPI(userName, token)

    const updateUser = (credentials) => updateAPI(credentials, token)

    const getNewVideo = (videoId) => getNewVideoApi(videoId, token)

    const deleteVideo = (videoId) => deleteVideoApi(videoId, token)

    return(
        <AuthContext.Provider value={{ token, user, login, logout, deleteUser, updateUser, getNewVideo, deleteVideo }}>
            {children}
        </AuthContext.Provider>
    )

}
export const useAuth = () => useContext(AuthContext)