import Header from "../components/Header";
import Page from "../components/Page";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import Button from "../components/Button";
import {useEffect} from "react";


export default function Delete() {
    const {user, logout, deleteUser} = useAuth()

    useEffect(()=>{
        return logout
    },[() => deleteUser(user.userName)])


    const handleDelete = () => {
        deleteUser(user.userName)
            .then(logout)
    }

    if(!user)
    {
        return <Redirect to = "/"/>
    }

    return (
        <Page>
            <Main>
                <Header title="Löschen"/>
                <p>{user.userName}, möchtest Du Dein Profil wirklich löschen?</p>
                <Button as = {Link} to = "/profile">Nein, natürlich nicht</Button>
                <Button as = {Link} to = "/" onClick = {handleDelete}>Ja</Button>
            </Main>
        </Page>
    )
}