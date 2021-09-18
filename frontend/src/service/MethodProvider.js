import {useContext} from "react";
import { deleteUser as deleteAPI, updateUser as updateAPI, getNewVideo as getNewVideoApi, deleteVideo as deleteVideoApi, getAllUser as getAllUserApi} from "../service/apiService";
import MethodContext from "./MethodContext";
import {useAuth} from "../auth/AuthProvider";

export default function MethodProvider ({children}) {

    const {token} = useAuth()


    console.log(token)


    const deleteUser = (userName) => deleteAPI(userName, token)

    const updateUser = (credentials) => updateAPI(credentials, token)

    const getNewVideo = (videoId) => getNewVideoApi(videoId, token)

    const deleteVideo = (videoId) => deleteVideoApi(videoId, token)

    const getAllUser = (token) => getAllUserApi(token)

    return(
        <MethodContext.Provider value={{deleteUser, updateUser,
            getNewVideo, deleteVideo, getAllUser }}>
            {children}
        </MethodContext.Provider>
    )

}
export const useMethod = () => useContext(MethodContext)