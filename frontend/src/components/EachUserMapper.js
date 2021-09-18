import UserGallery from "./UserGallery";
import {useEffect, useState} from "react";
import {useAuth} from "../auth/AuthProvider";


export default function EachUserMapper(){
    const {token, getAllUser} = useAuth()
    const [allUser, setAllUser] = useState([])

    useEffect(() => {
        getAllUser(token).then(setAllUser)
            .catch(error => console.error(error))
    }, [getAllUser, token])

    const reloadUserPage = () =>{
        getAllUser(token)
            .then(setAllUser)
    }

    const eachUserList = allUser.map(fetchedUser => (
        <UserGallery fetchedUserName = {fetchedUser.userName} key = {fetchedUser.id} reloadUserPage = {reloadUserPage}
        />))

    return(
        <div>{eachUserList}</div>
    )

}