import Header from "../components/styled/Header";
import styled from 'styled-components/macro'
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import Main from "../components/Main";
import {Link} from "react-router-dom";
import Button from "../components/styled/Button";
import {useAuth} from "../auth/AuthProvider";

export default function StartScreen() {
    const {user} = useAuth()

    return (
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title="CancerCompanion"></Header>

                <Img src="CancerCompanion.png" alt="fb"/>

                <Button as={Link} to ="/login">Community</Button>
                <Button as={Link} to ="/info">Informationen</Button>
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



