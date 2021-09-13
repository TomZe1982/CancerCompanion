import Page from "../components/Page";
import Header from "../components/Header";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link, NavLink, Redirect} from "react-router-dom";
import Button from "../components/Button";




export default function Profile(){
    const {user, token} = useAuth()

    if(!token){
   return  <Redirect to = "/login"/>
    }

    return(
        <Page>
            <NavLink to="/">Home</NavLink>
            <Main>
                <Header title = "Profile von " />
                <h1>{user.userName}</h1>
                <Button as={Link} to = "/logout" >Logout</Button>
                <Button as={Link} to = "/delete" >Profil löschen</Button>
            </Main>
        </Page>
    )
}