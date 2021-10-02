import Header from "../components/styled/Header";
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import Main from "../components/Main";
import {Link} from "react-router-dom";
import {useAuth} from "../auth/AuthProvider";
import StartScreenButton from "../components/styled/StartScreenButton";
import styled from "styled-components/macro";
import Pulsation from "../components/Pulsation";


export default function StartScreen() {
    const {user} = useAuth()

    return (
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title="CancerCompanion"></Header>

                <Pulsation/>

            </Main>
            <ButtonGroup>
            <StartScreenButton as={Link} to ="/login">Community</StartScreenButton>
            <StartScreenButton as={Link} to ="/info">Informationen</StartScreenButton>
            </ButtonGroup>
        </Page>
    )
}

const ButtonGroup = styled.div`
display: flex;
  justify-content: space-evenly;
`
