import NavBar from "../components/NavBar";
import {useAuth} from "../auth/AuthProvider";
import Main from "../components/Main";
import Header from "../components/Header";
import Page from "../components/Page";
import {useState} from "react";
import TextField from "../components/TextField";
import Button from "../components/Button";
import Error from "../components/Error";
import Loading from "../components/Loading";
import EachUserMapper from "../components/EachUserMapper";



export default function Admin() {
    const {user, getNewVideo} = useAuth()
    const [newVideoId, setNewVideoId] = useState("")
    const [loading, setLoading] = useState(false)


    const handleSubmitUpload = (event) => {
        event.preventDefault()
        setLoading(true)
        getNewVideo(newVideoId)
            .catch(error => console.error(error),
                setLoading(false))
            .finally(() => setNewVideoId(""))
    }


    const handleOnChangeUpload = (event) => {
        setNewVideoId(event.target.value)
    }


    return (
        <Page>
            <NavBar user={user}/>
            {loading && <Loading/>}
            {!loading && (<Main>
                    <Header title="Admin´s Page"/>
                    <form onSubmit={handleSubmitUpload}>
                    <Header title={user.userName}/>
                    <p>Neue Videos hochladen</p>
                    <TextField
                        title="Video"
                        name="video"
                        value={newVideoId || ""}
                        onChange={handleOnChangeUpload}/>
                    {newVideoId !== "" ?
                        <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
                    </form>
                    <section><EachUserMapper/></section>
                </Main>
            )}

        </Page>
    )

}