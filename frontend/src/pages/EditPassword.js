import Page from "../components/Page";
import Main from "../components/Main";
import Header from "../components/Header";
import NavBar from "../components/NavBar";
import {useAuth} from "../auth/AuthProvider";
import TextField from "../components/TextField";
import Button from "../components/Button";
import Error from "../components/Error";
import {useLayoutEffect, useState} from "react";
import {Redirect} from "react-router-dom";
import {getUser} from "../service/apiService";


export default function EditPassword() {
    const {user, token, updateUser} = useAuth()
    const [userToChange, setUserToChange] = useState({})
    const [credentials, setCredentials] = useState({})
    const [changedCredentials, setChangedCredentials] = useState()

    useLayoutEffect(() => {
        getUser(user.userName, token)
            .then(setUserToChange)
    }, [user.userName, token])


    const handleOnChange = (event) => {
        setCredentials({
            userName: userToChange.userName,
            email: userToChange.email, ...credentials,
            [event.target.name]: event.target.value
        })
        setChangedCredentials(changedCredentials)
    }

    if(changedCredentials)
    {
        return <Redirect to = "/profile"/>
    }

    console.log(credentials)

    const handleSubmit = (event) => {
        event.preventDefault()
        updateUser(credentials)
            .then(changedCredentials => setChangedCredentials(changedCredentials))

    }

    if (changedCredentials) {
        return <Redirect to="/login"/>
    }

    return (<Page>
            <NavBar user = {user}/>
            <Main as="form" onSubmit={handleSubmit}>
                <Header title="Neues Passwort erstellen"/>
                <TextField
                    title="Passwort"
                    name="password"
                    value={credentials.password || ""}
                    onChange={handleOnChange}/>
                {(credentials.password !== "") ?
                    <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
            </Main>
        </Page>

    )

}