import NavBar from "../components/NavBar";
import EachInfo from "../components/information/EachInfo";
import Page from "../components/Page";
import Main from "../components/Main";
import Header from "../components/styled/Header";


export default function EachInformationPage () {

    return(
        <Page>
            <NavBar/>
            <Main>
                <Header title = "Information"/>
                <section>
                    <EachInfo/>
                </section>
            </Main>
        </Page>
    )
}