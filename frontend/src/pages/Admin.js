import NavBar from "../components/NavBar";
import {useAuth} from "../auth/AuthProvider";
import Main from "../components/Main";
import Header from "../components/Header";


export default function Admin () {
    const {user} = useAuth()

    return(
        <page>
            <NavBar user = {user}/>
            <Main>
                <Header title={user.userName}/>
            </Main>
        </page>
    )

}