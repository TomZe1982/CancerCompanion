import Page from "./Page";
import NavBar from "./NavBar";
import Main from "./Main";
import Header from "./Header";
import {useAuth} from "../auth/AuthProvider";



export default function AdminError(){
    const {user} = useAuth()

    return(
        <Page>
            <NavBar user = {user}/>
            <Header> title = "Oh oh" </Header>
            <Main>
                <h1>{user.userName}, you are fucking admin !!!</h1>
            </Main>
        </Page>
    )

}