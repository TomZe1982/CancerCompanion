import {useContext, useState} from "react";
import jwt from 'jsonwebtoken'
import {
    deleteUser as deleteAPI, deleteVideo as deleteVideoApi, getAllUser as getAllUserApi,
    getNewVideo as getNewVideoApi,
    getToken,
    updateUser as updateAPI
} from "../service/apiService";
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

    const getAllUser = (token) => getAllUserApi(token)


    return(
        <AuthContext.Provider value={{ token, user, login, logout, deleteUser, updateUser,
        getNewVideo, getAllUser, deleteVideo}}>
            {children}
        </AuthContext.Provider>
    )

}
export const useAuth = () => useContext(AuthContext)