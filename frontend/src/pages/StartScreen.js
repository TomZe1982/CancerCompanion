import Header from "../components/styled/Header";
import styled from 'styled-components/macro'
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import Main from "../components/Main";
import {Link} from "react-router-dom";
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

const Button = styled.button`
  display: inline-block;
  padding: var(--size-l);
  background: var(--accent);
  border: 1px solid var(--accent);
  color: var(--neutral-light);
  font-size: 1em;
  border-radius: 50%;
`


