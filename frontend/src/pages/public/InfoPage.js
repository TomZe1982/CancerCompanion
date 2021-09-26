import NavBar from "../../components/NavBar";
import Header from "../../components/styled/Header";
import Page from "../../components/Page";
import Main from "../../components/Main";
import InfoCard from "../../components/information/InfoCard";
import {useAuth} from "../../auth/AuthProvider";



export default function InfoPage () {
    const {user} = useAuth()

    if(user){
    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title = "Allgemeine Informationen"/>
                <InfoCard/>
            </Main>
        </Page>
    )
    }
    return(
        <Page>
            <NavBar/>
            <Main>
                <Header title = "Allgemeine Informationen"/>
                <InfoCard/>
            </Main>
        </Page>
    )

}