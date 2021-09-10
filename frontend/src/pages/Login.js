import Header from "../components/Header";
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import {NavLink, Redirect} from "react-router-dom";
import Main from "../components/Main";
import TextField from "../components/TextField";
import Button from "../components/Button";
import {useState} from "react";
import {useAuth} from "../auth/AuthProvider";


export default function Login() {
    const {login, user} = useAuth()
    const [credentials, setCredentials] = useState({});

    const handleOnChange = (event) => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }
    console.log(credentials)

    const submitHandler = (event) => {
        event.preventDefault()
        login(credentials)
            .catch(error => console.error(error))
    }

    if (user) {
        return <Redirect to="/profile"/>
    }

    return (
        <Page>
            <NavBar/>
            <Header title="Login"/>
            <Main as="form" onSubmit={submitHandler}>
                <TextField
                    title="Benutzername"
                    name="userName"
                    value={credentials.userName || ""}
                    onChange={handleOnChange}
                />
                <TextField
                    title="Passwort"
                    name="password"
                    value={credentials.password || ""}
                    onChange={handleOnChange}
                />
                <Button>Login</Button>
            </Main>
            <NavLink to="/register">Registrieren</NavLink>

        </Page>
    )
}


