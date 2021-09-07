import Header from "../components/Header";
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import {NavLink} from "react-router-dom";
import Main from "../components/Main";
import TextField from "../components/TextField";
import Button from "../components/Button";


const initialState = {
    username: '',
    password: '',
}


export default function Login() {


    const submitHandler = (event) => {
        event.preventDefault()

    }

    const handleCredentialsChange = (event) => {

    }



    return (
        <Page>
            <NavBar/>
            <Header title="Login"/>
            <Main as="form" onSubmit={submitHandler}>
                <TextField
                    title="Benutzername"
                    name="Benutzername"
                    value={initialState.username}
                onChange={handleCredentialsChange}/>
                <TextField
                    title="Passwort"
                    name="Passwort"
                    value={initialState.password}
                onChange={handleCredentialsChange}/>
                <Button>Login</Button>
            </Main>
            <NavLink to="/register">Registrieren</NavLink>
        </Page>
    )
}


