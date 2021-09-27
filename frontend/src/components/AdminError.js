import Page from "./Page";
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
height: 90%;
  width: 50%;
`