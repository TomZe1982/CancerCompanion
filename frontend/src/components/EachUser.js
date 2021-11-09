import Button from "./styled/Button";
import {useState} from "react";
import {deleteUser, resetPassword} from "../service/apiService";
import {useAuth} from "../auth/AuthProvider";
import Error from "./Error";
import Box from "./styled/Box";
import {Link} from "react-router-dom";


export default function EachUser({fetchedUserName, reloadUserPage}) {
    const {token} = useAuth(
    )
    const [resetUserPassword, setResetUserPassword] = useState("")
    const [error, setError] = useState()

    const handleResetPassword = () => {
        resetPassword(fetchedUserName, token)
            .then(response => setResetUserPassword(response))
            .then(reloadUserPage)
            .catch(error => setError(error))
    }
    const handleDeleteUser = () => {
        deleteUser(fetchedUserName, token)
            .then(reloadUserPage)
            .catch(error => setError(error))
    }

    return (
        <div>
            <Box>
                <section>{fetchedUserName}
                </section>
                <details>
                    <Button onClick={handleDeleteUser}>User löschen</Button>
                    <Button onClick={handleResetPassword}>Passwort zurücksetzen</Button>
                    <Link to = {`/therapy/${fetchedUserName}`}>Therapie hinzufügen</Link>
                    <Link to = {`/timeline/${fetchedUserName}`}>Therapie Timeline</Link>
                    <h1>{resetUserPassword.password}</h1>
                    {error && <Error>{error.response.data.error}</Error>}
                </details>
            </Box>
        </div>
    )
}

