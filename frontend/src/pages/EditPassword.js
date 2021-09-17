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
import Loading from "../components/Loading";


export default function EditPassword() {
    const {user, token, updateUser} = useAuth()
    const [userToChange, setUserToChange] = useState({})
    const [credentials, setCredentials] = useState({})
    const [changedCredentials, setChangedCredentials] = useState()
    const [loading, setLoading] = useState(false)

    useLayoutEffect(() => {
        setLoading(true)
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

    if (changedCredentials) {
        return <Redirect to="/profile"/>
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        setLoading(true)
        updateUser(credentials)
            .then(changedCredentials => setChangedCredentials(changedCredentials))
            .catch(error => console.error(error), setLoading(false))

    }

    if (changedCredentials) {
        return <Redirect to="/login"/>
    }

    return (<Page>
            <NavBar user={user}/>
            {loading && <Loading/>}
            {!loading && (
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
            )}
        </Page>

    )

}