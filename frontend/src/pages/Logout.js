import Page from "../components/Page";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import {useAuth} from "../auth/AuthProvider";
import Button from "../components/Button";
import {Redirect} from "react-router-dom";
import Main from "../components/Main";



export default function Logout () {
    const {user, logout} = useAuth()


    if(!user){
        return <Redirect to = "/"/>
    }

    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title = "Logout"/>
            <p>{user.userName}, möchtest Du Dich wirklich ausloggen?</p>
            <Button onClick = {logout}>Logout</Button>
            </Main>
        </Page>
    )

}