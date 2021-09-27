import Header from "../components/styled/Header";
import styled from 'styled-components/macro'
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import Main from "../components/Main";
import {Link} from "react-router-dom";
import {useAuth} from "../auth/AuthProvider";
import StartScreenButton from "../components/styled/StartScreenButton";

export default function StartScreen() {
    const {user} = useAuth()

    return (
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title="CancerCompanion"></Header>

                <Img src="CancerCompanion.png" alt="fb"/>

                <StartScreenButton as={Link} to ="/login">Community</StartScreenButton>
                <StartScreenButton as={Link} to ="/info">Informationen</StartScreenButton>
            </Main>
        </Page>
    )
}

const Img = styled.img`
  display: grid;
  justify-content: center;
  align-content: center;
  width: 80%;
  height: 100%;
`


