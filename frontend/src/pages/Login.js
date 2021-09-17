import Header from "../components/Header";
import Page from "../components/Page";
import NavBar from "../components/NavBar";
import {Link, Redirect} from "react-router-dom";
import Main from "../components/Main";
import TextField from "../components/TextField";
import Button from "../components/Button";
import {useState} from "react";
import {useAuth} from "../auth/AuthProvider";
import Loading from "../components/Loading";
import Error from "../components/Error";


export default function Login() {
    const {login, user} = useAuth()
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()
    const [credentials, setCredentials] = useState({});

    const handleOnChange = (event) => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }


    const submitHandler = (event) => {
        event.preventDefault()
        setLoading(true)
        setError()
        login(credentials)
            .catch(error => {
                setError(error)
                setLoading(false)
            })
    }


    if (user) {
        return <Redirect to="/profile" user={user}/>
    }

    return (
        <Page>
            <NavBar user = {user}/>
            {loading && <Loading/>}
            {!loading && (
            <Main as="form" onSubmit={submitHandler}>
                <Header title="Login"/>
                <TextField
                    title="Benutzername"
                    name="userName"
                    value={credentials.userName || ""}
                    onChange={handleOnChange}
                />
                <TextField
                    title="Passwort"
                    name="password"
                    type="password"
                    value={credentials.password || ""}
                    onChange={handleOnChange}
                />
                <Button>Login</Button>
            </Main>)}
            {error && <Error>{error.message}</Error>}
            <Button as={Link} to = "/register" >Registrieren</Button>

        </Page>
    )
}


