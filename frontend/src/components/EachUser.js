
import Button from "../components/Button";
import {useAuth} from "../auth/AuthProvider";
import { useState} from "react";



export default function EachUser( {key,  fetchedUserName, reloadUserPage } ) {
    const { deleteUser, resetPassword } = useAuth()
    const [resetUserPassword, setResetUserPassword] = useState("")

    console.log(fetchedUserName)

    const handleResetPassword = () => {
        resetPassword(fetchedUserName)
            .then(response => setResetUserPassword(response))
            .then(reloadUserPage)
            .catch(error => console.error(error))
    }
    const handleDeleteUser = () => {
        deleteUser(fetchedUserName)
            .then(reloadUserPage)
            .catch(error => console.error(error))
    }

    return (
        <div>
             <section>{fetchedUserName}
            </section>
            <Button onClick={handleDeleteUser}>User löschen</Button>
            <Button onClick={handleResetPassword}>Passwort zurücksetzen</Button>
            <h1>{resetUserPassword.password}</h1>
        </div>
    )
}

