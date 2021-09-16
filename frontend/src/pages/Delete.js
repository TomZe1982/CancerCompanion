import Header from "../components/Header";
import Page from "../components/Page";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import Button from "../components/Button";
import {useState} from "react";
import NavBar from "../components/NavBar";



export default function Delete() {
    const {user, logout, deleteUser} = useAuth()
    const [deletedUser, setDeletedUser] = useState()

    if(user.role === "admin"){
        return <Redirect to = "/adminerror"/>
    }

    const handleDelete = () => {
        deleteUser(user.userName)
            .then(deletedUser => setDeletedUser(deletedUser))
            .then(logout)
    }


    if(deletedUser)
    {
        return <Redirect to = "/"/>
    }

    return (
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title="Löschen"/>
                <p>{user.userName}, möchtest Du Dein Profil wirklich löschen?</p>
                <Button as = {Link} to = "/profile">Nein, natürlich nicht</Button>
                <Button as = {Link} to = "/" onClick = {handleDelete}>Ja</Button>
            </Main>
        </Page>
    )
}