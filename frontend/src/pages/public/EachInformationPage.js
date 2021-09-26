import NavBar from "../../components/NavBar";
import EachInfo from "../../components/information/EachInfo";
import Page from "../../components/Page";
import Main from "../../components/Main";
import Header from "../../components/styled/Header";
import {useAuth} from "../../auth/AuthProvider";


export default function EachInformationPage () {
    const{user} = useAuth()

    if(user) {
        return (
            <Page>
                <NavBar user={user}/>
                <Main>
                    <Header title="Information"/>
                    <section>
                        <EachInfo/>
                    </section>
                </Main>
            </Page>
        )
    }
    return (
        <Page>
            <NavBar/>
            <Main>
                <Header title="Information"/>
                <section>
                    <EachInfo/>
                </section>
            </Main>
        </Page>
    )
}