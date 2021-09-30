
import NavBar from "./NavBar";
import Main from "./Main";
import Header from "./styled/Header";
import {useAuth} from "../auth/AuthProvider";
import styled from "styled-components/macro";



export default function AdminError(){
    const {user} = useAuth()

    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title = "Oh oh"/>
                <Img src="alarm.png" alt="red_light"/>
                <h1 align="center">{user.userName}, you are admin !!!</h1>
            </Main>
        </Page>
    )

}

const Img = styled.img`
height: 300%;
  width: 200%;
`

const Page = styled.div`
  background: var(--background);
  background-repeat: no-repeat;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: grid;
  place-items: center;
  grid-template-rows: min-content 1fr min-content;   
`