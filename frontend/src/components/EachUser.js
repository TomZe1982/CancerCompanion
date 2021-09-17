
import Button from "../components/Button";
import {useAuth} from "../auth/AuthProvider";



export default function EachUser( { fetchedUserName, reloadUserPage } ) {
    const {deleteUser} = useAuth()

    console.log(fetchedUserName)

    const handleDeleteUser = () => {
        deleteUser(fetchedUserName)
            .then(reloadUserPage)
            .catch(error => console.error(error))
    }

    return (
        <div>
            <section>{fetchedUserName}</section>
            <Button onClick={handleDeleteUser}>User löschen</Button>
        </div>
    )
}