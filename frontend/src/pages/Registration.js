import Page from "../components/Page";
import Main from "../components/Main";
import NavBar from "../components/NavBar";
import TextField from "../components/TextField";
import Button from "../components/Button";
import {useState} from "react";
import {createUser} from "../service/apiService";
import Header from "../components/Header";



export default function Registration() {
    const [credentials, setCredentials] = useState({});


    const handleOnChange = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }

    console.log(credentials)

    const handleSubmit = event => {
        event.preventDefault()
        createUser(credentials)
            .catch(error => console.error(error))
            .finally(() => setCredentials({credentials: ""}))
    }




return (
    <Page>
        <NavBar/>
        <Main as="form" onSubmit={handleSubmit}>
            <Header title="Registrieren"/>
            <TextField
                title="Benutzername"
                name="userName"
                value={credentials.userName || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Email"
                name="email"
                value={credentials.email || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Passwort"
                name="password"
                value={credentials.password || ""}
                onChange={handleOnChange}/>
            <Button>Bestätigen</Button>
        </Main>
    </Page>

)

}