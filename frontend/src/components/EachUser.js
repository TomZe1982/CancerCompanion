
import Button from "../components/Button";
import {useAuth} from "../auth/AuthProvider";





export default function EachUser( { fetchedUserName, reloadUserPage } ) {
    const { deleteUser, resetPassword } = useAuth()


    const handleResetPassword = () => {
        resetPassword(fetchedUserName)
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
        </div>
    )
}

