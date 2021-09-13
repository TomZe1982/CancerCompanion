import Page from "./Page";
import NavBar from "./NavBar";
import Header from "./Header";
import {useAuth} from "../auth/AuthProvider";
import Button from "../components/Button";
import {Redirect} from "react-router-dom";


export default function Logout () {
const {user, logout} = useAuth()

    if(!user){
        return <Redirect to = "/login"/>
    }

    return(
        <Page>
            <NavBar/>
                <Header title = "Logout"/>
            <p>{user.userName}, möchtest Du Dich wirklich ausloggen?</p>
            <Button onClick = {logout}>Logout</Button>
        </Page>
    )

}