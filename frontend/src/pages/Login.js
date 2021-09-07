import Header from "../components/Header";
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import {NavLink} from "react-router-dom";
import Main from "../components/Main";
import TextField from "../components/TextField";
import Button from "../components/Button";
import {useState} from "react";


export default function Login() {
    const [credentials, setCredentials] = useState({});

    const submitHandler = (event) => {
        event.preventDefault()

    }
    const handleCredentialsChange = (event) => {
        setCredentials({...credentials, [event.target.name]: event.target.value})

    }
    return (
        <Page>
            <NavBar/>
            <Header title="Login"/>
            <Main as="form" onSubmit={submitHandler}>
                <TextField
                    title="Benutzername"
                    name="Benutzername"
                    value={credentials.userName}
                onChange={handleCredentialsChange}/>
                <TextField
                    title="Passwort"
                    name="Passwort"
                    value={credentials.password}
                onChange={handleCredentialsChange}/>
                <Button>Login</Button>
            </Main>
            <NavLink to="/register">Registrieren</NavLink>
        </Page>
    )
}


