import Page from "../components/Page";
import Header from "../components/Header";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {NavLink, Redirect} from "react-router-dom";


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
            </Main>
        </Page>
    )
}