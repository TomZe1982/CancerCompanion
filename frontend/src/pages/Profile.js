import Page from "../components/Page";
import Header from "../components/Header";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";


export default function Profile(){
    const {user} = useAuth()


    return(
        <Page>
            <Main>
                <Header title = "Profile von " />
                <h1>{user.userName}</h1>
            </Main>
        </Page>
    )
}