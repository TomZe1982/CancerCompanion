import NavBar from "../components/NavBar";
import {useAuth} from "../auth/AuthProvider";
import Main from "../components/Main";
import Header from "../components/Header";
import Page from "../components/Page";
import {useState} from "react";
import TextField from "../components/TextField";
import Button from "../components/Button";
import Error from "../components/Error";


export default function Admin () {
    const {user, getNewVideo} = useAuth()
    const[newVideoId, setNewVideoId] = useState()

    const handleSubmit = (event) => {
        event.preventDefault()
        getNewVideo(newVideoId)
    }

    const handleOnChange = (event) => {
        setNewVideoId(event.target.value)
    }



    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Main as="form" onSubmit={handleSubmit}>
                    <Header title={user.userName}/>
                    <p>Neue Videos hochladen</p>
                    <TextField
                        title="Video"
                        name="video"
                        value={newVideoId || ""}
                        onChange={handleOnChange}/>
                    {newVideoId !== "" ?
                        <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
                </Main>
            </Main>
        </Page>
    )

}