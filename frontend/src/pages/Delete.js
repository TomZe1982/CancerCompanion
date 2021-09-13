import Header from "../components/Header";
import Page from "../components/Page";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link} from "react-router-dom";
import Button from "../components/Button";


export default function Delete() {
    const {user} = useAuth()


    return (
        <Page>
            <Main>
                <Header title="Löschen"/>
                <h1>{user.userName}, möchtest Du Dein Profil wirklich löschen?</h1>
                <Button as = {Link} to = "/profile">Nein, natürlich nicht</Button>
                <Button onClick = {""}>Ja</Button>

            </Main>
        </Page>
    )

}