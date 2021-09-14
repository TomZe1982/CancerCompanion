import Header from "../components/Header";
import Page from "../components/Page";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import Button from "../components/Button";


export default function Delete() {
    const {user, logout, deleteUser} = useAuth()

    if(!user){
       return <Redirect to = "/"/>
    }

    return (
        <Page>
            <Main>
                <Header title="Löschen"/>
                <p>{user.userName}, möchtest Du Dein Profil wirklich löschen?</p>
                <Button as = {Link} to = "/profile">Nein, natürlich nicht</Button>
                <Button as = {Link} to = "/" onClick = {(() => deleteUser(user.userName)) && logout}>Ja</Button>
            </Main>
        </Page>
    )

}