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
                onChange={handleOnChange}
            />
            <TextField
                title="Vorname"
                name="firstName"
                value={credentials.firstName || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Nachname"
                name="secondName"
                value={credentials.secondName || ""}
                onChange={handleOnChange}/>
            <TextField
                title="E-Mail"
                name="email"
                value={credentials.email || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Straße"
                name="street"
                value={credentials.street || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Hausnummer"
                name="number"
                value={credentials.number || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Postleitzahl"
                name="zipCode"
                value={credentials.zipCode || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Stadt"
                name="city"
                value={credentials.city || ""}
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