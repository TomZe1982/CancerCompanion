import {useContext, useState} from "react";
import jwt from 'jsonwebtoken'
<<<<<<< HEAD
import {getToken, deleteUser as deleteAPI, updateUser as updateAPI, getNewVideo as getNewVideoApi, deleteVideo as deleteVideoApi, getAllUser as getAllUserApi, resetPassword as resetPasswordApi} from "../service/apiService";
=======
import {
    deleteUser as deleteAPI, deleteVideo as deleteVideoApi, getAllUser as getAllUserApi,
    getNewVideo as getNewVideoApi,
    getToken,
    updateUser as updateAPI
} from "../service/apiService";
>>>>>>> development
import AuthContext from "./AuthContext";

export default function AuthProvider ( { children } ) {

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

    const getAllUser = () => getAllUserApi(token)

    const resetPassword = (userName) => resetPasswordApi(userName, token)


    return(
        <AuthContext.Provider value={{ token, user, login, logout, deleteUser, updateUser,
<<<<<<< HEAD
                                        getNewVideo, deleteVideo, getAllUser, resetPassword }}>
=======
        getNewVideo, getAllUser, deleteVideo}}>
>>>>>>> development
            {children}
        </AuthContext.Provider>
    )

}
export const useAuth = () => useContext(AuthContext)