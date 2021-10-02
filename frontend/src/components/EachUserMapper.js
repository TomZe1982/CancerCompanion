import UserGallery from "./UserGallery";
import {useEffect, useState} from "react";
import {useAuth} from "../auth/AuthProvider";
import {getAllUser} from "../service/apiService";
import Error from "./Error";
import TextField from "./TextField";



export default function EachUserMapper() {
    const {token} = useAuth()
    const [allUser, setAllUser] = useState([])
    const [error, setError] = useState()
    const [foundUser, setFoundUser] = useState("")

    const handleChange = (event) => {
        setFoundUser(event.target.value)
    }

    useEffect(() => {
        getAllUser(token).then(setAllUser)
            .catch(error => setError(error))
    }, [token])

    const reloadUserPage = () => {
        getAllUser(token)
            .then(setAllUser)
    }

    const filteredUser = allUser.filter(fetchedUser => (
        fetchedUser.userName.toLowerCase().includes(foundUser.toLowerCase())))

    const eachUserListToUpdate = filteredUser.map(fetchedUser => (
        <UserGallery fetchedUserPassword={fetchedUser.password} fetchedUserName={fetchedUser.userName}
                     key={fetchedUser.id} reloadUserPage={reloadUserPage}
        />)
    )

    return (
        <div>
            <TextField
                title="Nach Usern suchen"
                name="userName"
                value={foundUser}
                onChange={handleChange}
            />
          <section>{eachUserListToUpdate}</section>
            {error && <Error>{ error.response.data.error}</Error>}
        </div>

    )

}