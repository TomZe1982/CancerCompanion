
import Button from "../components/Button";
import { useState} from "react";
import {deleteUser, resetPassword} from "../service/apiService";
import {useAuth} from "../auth/AuthProvider";



export default function EachUser( { fetchedUserName, reloadUserPage } ) {
    const {token} = useAuth(
    )
    const [resetUserPassword, setResetUserPassword] = useState("")

    const handleResetPassword = () => {
        resetPassword(fetchedUserName, token)
            .then(response => setResetUserPassword(response))
            .then(reloadUserPage)
            .catch(error => console.error(error))
    }
    const handleDeleteUser = () => {
        deleteUser(fetchedUserName, token)
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

