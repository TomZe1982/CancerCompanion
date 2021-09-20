import UserGallery from "./UserGallery";
import {useEffect, useState} from "react";
import {useAuth} from "../auth/AuthProvider";



export default function EachUserMapper() {
    const {token, getAllUser} = useAuth()
    const [allUser, setAllUser] = useState([])

    useEffect(() => {
        getAllUser(token).then(setAllUser)
            .catch(error => console.error(error))
    }, [getAllUser, token])

    const reloadUserPage = () => {
        getAllUser(token)
            .then(setAllUser)
    }

    const eachUserListToUpdate = allUser.map(fetchedUser => (
        <UserGallery fetchedUserPassword={fetchedUser.password} fetchedUserName={fetchedUser.userName}
                     key={fetchedUser.id} reloadUserPage={reloadUserPage}
        />)
    )

    return (
        <div>
          <section>{eachUserListToUpdate}</section>
        </div>

    )

}