import NavBar from "../../components/NavBar";
import Page from "../../components/Page";
import Main from "../../components/Main";
import Header from "../../components/styled/Header";
import {useAuth} from "../../auth/AuthProvider";
import {useState} from "react";
import {postInfo} from "../../service/apiService";
import Button from "../../components/styled/Button";
import TextField from "../../components/TextField";
import Error from "../../components/Error";
import TextAreaUpdate from "../../components/TextAreaUpdate";
import Loading from "../../components/Loading";


export default function AddInformation() {
    const {token, user} = useAuth()

    const [credentials, setCredentials] = useState({})
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()

    const handleOnChange = (event) => {
        setCredentials( {...credentials, [event.target.name] : event.target.value})
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        setLoading(true)
        postInfo(credentials, token )
            .then(response => console.log(response))
            .then(() => setCredentials({credentials: ""}))
            .catch(error => setError(error))
            .finally(() => setLoading(false))
    }

    return (
        <Page>
            <NavBar user = {user} />
            {loading && <Loading/>}
            {!loading && (
            <Main as = "form" onSubmit={handleSubmit}>
                <Header title="Admin Add Information"/>
                    <TextField title ="Titel"
                              name="title"
                              value={credentials.title || ""}
                              onChange={handleOnChange}
                    />
                    <TextAreaUpdate title ="Text"
                              name="info"
                              value={credentials.info || ""}
                              onChange={handleOnChange}
                    />
                {(credentials.title !== "" && credentials.info !== "") ?
                    <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
            </Main>
            )}
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>
    )
}