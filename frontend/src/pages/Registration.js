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





export default function Registration() {
    const [credentials, setCredentials] = useState({});
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()

    const handleOnChange = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }

    console.log(credentials)

    const handleSubmit = event => {
        event.preventDefault()
        setError()
        createUser(credentials)
            .catch(error => {setError(error)
            setLoading(false)})
            .finally(() => setCredentials({credentials: ""}))
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
            <Button>Bestätigen</Button>
        </Main>
        )}
        {error && <Error>{error.message}</Error>}
    </Page>

)

}