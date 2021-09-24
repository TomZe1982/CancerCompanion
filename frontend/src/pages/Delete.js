import Header from "../components/Header";
import Page from "../components/Page";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import Button from "../components/Button";
import {useState} from "react";
import NavBar from "../components/NavBar";
import Loading from "../components/Loading";
import {deleteUser} from "../service/apiService";



export default function Delete() {
    const {user, token, logout} = useAuth()
    const [deletedUser, setDeletedUser] = useState()
    const [loading, setLoading] = useState(false)

    if(user.role === "admin"){
        return <Redirect to = "/adminerror"/>
    }

    const handleDelete = () => {
        setLoading(true)
        deleteUser(user.userName, token)
            .then(deletedUser => setDeletedUser(deletedUser))
            .then(logout)
            .catch(error => console.error(error), setLoading(false))
    }


    if(deletedUser)
    {
        return <Redirect to = "/"/>
    }

    return (
        <Page>
            <NavBar user = {user}/>
            <Header title="Löschen"/>
            {loading && <Loading/>}
            {!loading && ( <Main>
                <p>{user.userName}, möchtest Du Dein Profil wirklich löschen?</p>
                <Button as = {Link} to = "/profile">Nein, natürlich nicht</Button>
                <Button as = {Link} to = "/" onClick = {handleDelete}>Ja</Button>
            </Main>
                )}
        </Page>
    )
}