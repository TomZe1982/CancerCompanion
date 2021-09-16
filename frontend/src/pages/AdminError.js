import Page from "../components/Page";
import NavBar from "../components/NavBar";
import Main from "../components/Main";
import Header from "../components/Header";
import {useAuth} from "../auth/AuthProvider";


export default function AdminError(){
    const {user} = useAuth()

    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header> title = "Oh oh" </Header>
                <h1>{user.userName}, you are fucking admin !!!</h1>
            </Main>
        </Page>
    )

}