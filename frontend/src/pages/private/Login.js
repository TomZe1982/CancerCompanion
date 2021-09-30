import Header from "../../components/styled/Header";
import Page from "../../components/Page";
import NavBar from "../../components/NavBar";
import {Link, Redirect} from "react-router-dom";
import Main from "../../components/Main";
import TextField from "../../components/TextField";
import Button from "../../components/styled/Button";
import {useState} from "react";
import {useAuth} from "../../auth/AuthProvider";
import Loading from "../../components/Loading";
import Error from "../../components/Error";
import styled from "styled-components/macro";


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
            .catch(error => setError(error))
    }


    if (user) {
        return <Redirect to="/profile" user={user}/>
    }

    return (
        <Page>
            <NavBar user={user}/>
            <Main>
                <Header title="Login"/>
                {loading && <Loading/>}
                {!loading && (
                    <Form onSubmit={submitHandler}>
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
                    </Form>)}
                {error && <Error>{error.response.data.error}</Error>}
                <Button as={Link} to="/register">Registrieren</Button>
            </Main>

        </Page>
    )
}

const Form = styled.form`
  display: grid;
  align-content: center;
  place-items: center;
  place-content: start;
  justify-content: center;
  align-items: center;
  grid-gap: var(--size-xxl);
  padding: var(--size-xl);
  height: 100%;
  width: 100%;
  overflow-y: scroll;

`
