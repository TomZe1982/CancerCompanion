import Page from "../../components/Page";
import Main from "../../components/Main";
import NavBar from "../../components/NavBar";
import TextField from "../../components/TextField";
import Button from "../../components/styled/Button";
import {useState} from "react";
import {createUser} from "../../service/apiService";
import Header from "../../components/styled/Header";
import Loading from "../../components/Loading";
import Error from "../../components/Error";
import {Redirect} from "react-router-dom";
import {useAuth} from "../../auth/AuthProvider";






export default function Registration() {
    const{user} = useAuth()
    const [credentials, setCredentials] = useState({credentials: ""});
    const [loading] = useState(false)
    const [registeredUser, setRegisteredUser] = useState()
    const [passwordRepeat, setPasswordRepeat] = useState("")
    const [error, setError] = useState()


    const handleOnChange = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }

    const handleOnChangeRepeat = (event) => {
        setPasswordRepeat(event.target.value)
    }

    const handleSubmit = event => {
        event.preventDefault()
        setError()
        createUser(credentials)
            .then(registeredUser => setRegisteredUser(registeredUser))
            .catch(error => setError(error))
            .finally(() => setCredentials({credentials: ""}))
    }

    console.log(credentials)

    if(registeredUser){
        return <Redirect to = "/login"/>
    }

return (
    <Page>
        <NavBar user = {user}/>
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
                title="Avatar"
                name="avatar"
                value={credentials.avatar || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Passwort"
                name="password"
                type="password"
                value={credentials.password || ""}
                onChange={handleOnChange}/>
            <TextField
                title="Passwort"
                name="password"
                type="password"
                value={passwordRepeat}
                onChange={handleOnChangeRepeat}/>
            {(credentials.userName !== "" && credentials.email !== "" && credentials.password !== "" && credentials.password === passwordRepeat) ?
            <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
        </Main>
        )}
        {error && <Error>{ error.response.data.error}</Error>}
    </Page>

)

}