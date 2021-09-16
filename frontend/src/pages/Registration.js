import Page from "../components/Page";
import Main from "../components/Main";
import NavBar from "../components/NavBar";
import TextField from "../components/TextField";
import Button from "../components/Button";
import {useState} from "react";
import {createUser} from "../service/apiService";
import Header from "../components/Header";
import Loading from "../components/Loading";
import Error from "../components/Error";
import {Redirect} from "react-router-dom";






export default function Registration() {
    const [credentials, setCredentials] = useState("");
    const [loading, setLoading] = useState(false)
    const [registeredUser, setRegisteredUser] = useState()
    const [error, setError] = useState()


    const handleOnChange = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }

    const handleSubmit = event => {
        event.preventDefault()
        setError()
        createUser(credentials)
            .then(registeredUser => setRegisteredUser(registeredUser))
            .catch(error => {setError(error)
            setLoading(false)})
            .finally(() => setCredentials({credentials: ""}))
    }

    if(registeredUser){
        return <Redirect to = "/login"/>
    }

return (
    <Page>
        <NavBar/>
        {loading && <Loading/>}
        {!loading && (
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
            {(credentials.userName !== "" && credentials.email !== "" && credentials.password !== "") ?
            <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
        </Main>
        )}
        {error && <Error>{error.message}</Error>}
    </Page>

)

}