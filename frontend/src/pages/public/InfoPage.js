import NavBar from "../../components/NavBar";
import Header from "../../components/styled/Header";
import Page from "../../components/Page";
import Main from "../../components/Main";
import InfoCard from "../../components/information/InfoCard";
import {useAuth} from "../../auth/AuthProvider";



export default function InfoPage () {
    const {user} = useAuth()
    
    return(
        <Page>
            {user ? <NavBar user={user}/> :  <NavBar/>}
            <Main>
                <Header title = "Allgemeine Informationen"/>
                <InfoCard/>
            </Main>
        </Page>
    )

}