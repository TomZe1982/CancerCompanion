import Page from "../../components/Page";
import NavBar from "../../components/NavBar";
import Header from "../../components/styled/Header";
import {useAuth} from "../../auth/AuthProvider";
import Button from "../../components/styled/Button";
import {Redirect} from "react-router-dom";
import Main from "../../components/Main";



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
            <h2>{user.userName}, möchtest Du Dich wirklich ausloggen?</h2>
            <Button onClick = {logout}>Logout</Button>
            </Main>
        </Page>
    )

}