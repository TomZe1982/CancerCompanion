import NavBar from "../components/NavBar";
import Header from "../components/styled/Header";
import Page from "../components/Page";
import Main from "../components/Main";
import InfoCard from "../components/information/InfoCard";



export default function InfoPage () {


    return(
        <Page>
            <Main>
                <NavBar/>
                <Header title = "Allgemeine Informationen"/>
                <InfoCard/>
            </Main>
        </Page>
    )


}