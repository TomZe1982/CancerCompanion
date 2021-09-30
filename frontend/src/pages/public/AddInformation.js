import NavBar from "../../components/NavBar";
import Page from "../../components/Page";
import Main from "../../components/Main";
import Header from "../../components/styled/Header";
import {useAuth} from "../../auth/AuthProvider";
import TextArea from "../../components/TextArea";
import {useState} from "react";
import {postInfo} from "../../service/apiService";
import Button from "../../components/styled/Button";
import TextField from "../../components/TextField";
import Error from "../../components/Error";


export default function AddInformation() {
    const {token, user} = useAuth()

    const [credentials, setCredentials] = useState({})
    const [error, setError] = useState()

    const handleOnChange = (event) => {
        setCredentials( {...credentials, [event.target.name] : event.target.value})
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        postInfo(credentials, token )
            .then(response => console.log(response))
            .catch(error => setError(error))
            .finally(() => setCredentials({credentials: ""}))
    }

    return (
        <Page>
            <NavBar user = {user} />
            <Main as = "form" onSubmit={handleSubmit}>
                <Header title="Admin Add Information"/>
                    <TextField title ="Titel"
                              name="title"
                              value={credentials.title || ""}
                              onChange={handleOnChange}
                    />
                    <TextArea title ="Text"
                              name="info"
                              value={credentials.info || ""}
                              onChange={handleOnChange}
                    />
                {(credentials.title !== "" && credentials.info !== "") ?
                    <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
            </Main>
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>
    )
}