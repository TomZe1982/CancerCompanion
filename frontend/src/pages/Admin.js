import NavBar from "../components/NavBar";
import {useAuth} from "../auth/AuthProvider";
import Main from "../components/Main";
import Header from "../components/Header";
import Page from "../components/Page";


export default function Admin () {
    const {user} = useAuth()

    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title={user.userName}/>
            </Main>
        </Page>
    )

}