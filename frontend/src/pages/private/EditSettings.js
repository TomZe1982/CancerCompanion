import Page from "../../components/Page";
import Main from "../../components/Main";
import Header from "../../components/styled/Header";
import NavBar from "../../components/NavBar";
import {useAuth} from "../../auth/AuthProvider";
import TextField from "../../components/TextField";
import Button from "../../components/styled/Button";
import Error from "../../components/Error";
import {useEffect, useState} from "react";
import {getUser, updateUser} from "../../service/apiService";
import Box from "../../components/styled/Box";
import Loading from "../../components/Loading";


export default function EditSettings() {
    const {user, token} = useAuth()
    const [userToChange, setUserToChange] = useState({})
    const [credentials, setCredentials] = useState({})
    const [changedCredentials, setChangedCredentials] = useState()
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()

    useEffect(() => {
        setLoading(true)
        getUser(user.userName, token)
            .then(setUserToChange)
            .then(()=>setLoading(false))
            .catch(error => setError(error))
    }, [user.userName, token])

    const reloadPage = () => {
        setLoading(true)
        getUser(user.userName, token)
            .then(setUserToChange)
            .then(()=>setLoading(false))
            .catch(error => setError(error))
    }


    const handleOnChange = (event) => {
        setCredentials({
            userName: userToChange.userName,
            password: userToChange.password,
            email: userToChange.email, ...credentials,
            [event.target.name]: event.target.value
        })
            setChangedCredentials(changedCredentials)
    }


    const handleSubmit = (event) => {
        event.preventDefault()
        setLoading(true)
        updateUser(credentials, token)
            .then(changedCredentials => setChangedCredentials(changedCredentials))
            .then(reloadPage)
            .then(()=>setLoading(false))
            .catch(error => setError(error))
    }
/*
    if (changedCredentials) {
        return <Redirect to="/login"/>
    }*/

    return (<Page>
            <NavBar user = {user}/>
            {loading && <Loading/>}
            {!loading && (
            <Main as="form" onSubmit={handleSubmit}>
                <Header title="Profil bearbeiten"/>
                <Box>
                <p>Aktuelle Email-Adresse : {userToChange.email}</p>
                </Box>
                <TextField
                    title="Email"
                    name="email"
                    value={credentials.email || ""}
                    onChange={handleOnChange}/>
                {credentials.email !== "" ?
                    <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
            </Main>
            )}
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>

    )

}