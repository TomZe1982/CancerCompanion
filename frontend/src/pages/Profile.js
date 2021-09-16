import Page from "../components/Page";
import Header from "../components/Header";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import Button from "../components/Button";
import NavBar from "../components/NavBar";




export default function Profile(){
    const {user} = useAuth()

    if(!user){
   return  <Redirect to = "/login"/>
    }

    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title = "Profile von " />
                <h1>{user.userName}</h1>
                <Button as = {Link} to = "/editsettings">Email ändern</Button>
                <Button as = {Link} to = "/editpassword">Passwort ändern</Button>

                <Button as = {Link} to = "/delete" >Profil löschen</Button>
                <Button as = {Link} to = "/tutorials">Schmink Tutorials</Button>
            </Main>
        </Page>
    )
}