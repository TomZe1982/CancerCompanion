import Page from "../components/Page";
import Main from "../components/Main";
import Header from "../components/Header";
import NavBar from "../components/NavBar";
import {useAuth} from "../auth/AuthProvider";
import TextField from "../components/TextField";
import Button from "../components/Button";
import Error from "../components/Error";
import {useEffect, useState} from "react";
import {Redirect} from "react-router-dom";
import {getUser} from "../service/apiService";


export default function EditProfile () {
    const {user, token, updateUser} = useAuth()
    const [userToChange, setUserToChange] = useState({})
    const [credentials, setCredentials] = useState({})

    useEffect(() => {
        getUser(user.userName, token)
            .then(setUserToChange)
    },[])



    const handleOnChange = (event) => {
        setCredentials({userName: userToChange.userName, password: userToChange.password, email: userToChange.email, ...credentials, [event.target.name] : event.target.value})
    }


    console.log(credentials)

    const handleSubmit = (event) => {
        event.preventDefault()
        updateUser(credentials)

    }


    if(!user){
        return <Redirect to = "/login"/>
    }

    return(<Page>
            <NavBar/>
            <Main as = "form" onSubmit={handleSubmit}>
                <Header title = "Profil bearbeiten"/>
                <p>{userToChange.email}</p>
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
                    {(credentials.email !== "" || credentials.password !== "") ?
                    <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
            </Main>
        </Page>

    )

}