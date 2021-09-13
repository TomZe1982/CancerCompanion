import Header from "../components/Header";
import styled from 'styled-components/macro'
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import Main from "../components/Main";
import {Link} from "react-router-dom";
import Button from "../components/Button";

export default function StartScreen() {

    return (
        <Page>
            <NavBar/>
            <Main>
                <Header title="CancerCompanion"></Header>
                <div>
                <Img src="CC1.png" alt="fb"/>
                </div>
                <Button as={Link} to ="/login">Community</Button>
            </Main>
        </Page>
    )
}

const Img = styled.img`
  display: grid;
  justify-content: center;
  align-content: center;
`



