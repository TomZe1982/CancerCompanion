import Page from "../../components/Page";
import Main from "../../components/Main";
import Header from "../../components/styled/Header";
import NavBar from "../../components/NavBar";
import {useAuth} from "../../auth/AuthProvider";
import TextField from "../../components/TextField";
import Button from "../../components/styled/Button";
import Error from "../../components/Error";
import {useLayoutEffect, useState} from "react";
import {Redirect} from "react-router-dom";
import {getUser, updateUser} from "../../service/apiService";


export default function EditPassword() {
    const {user, token} = useAuth()
    const [userToChange, setUserToChange] = useState({})
    const [credentials, setCredentials] = useState({})
    const [changedCredentials, setChangedCredentials] = useState()
    const [passwordRepeat, setPasswordRepeat] = useState("")

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

    const handleOnChangeRepeat = (event) => {
        setPasswordRepeat(event.target.value)
    }

    if (changedCredentials) {
        return <Redirect to="/profile"/>
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        updateUser(credentials, token)
            .then(changedCredentials => setChangedCredentials(changedCredentials))
            .catch(error => console.error(error))

    }

    if (changedCredentials) {
        return <Redirect to="/login"/>
    }

    return (<Page>
            <NavBar user={user}/>
            <Main as="form" onSubmit={handleSubmit}>
                <Header title="Neues Passwort erstellen"/>
                <TextField
                    title="Passwort"
                    name="password"
                    type="password"
                    value={credentials.password || ""}
                    onChange={handleOnChange}/>
                <TextField
                    title="Passwort"
                    type="password"
                    value={passwordRepeat}
                    onChange={handleOnChangeRepeat}/>
                {(credentials.password !== "" && credentials.password === passwordRepeat) ?
                    <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
            </Main>
        </Page>

    )

}