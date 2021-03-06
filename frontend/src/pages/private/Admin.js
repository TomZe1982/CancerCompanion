import NavBar from "../../components/NavBar";
import {useAuth} from "../../auth/AuthProvider";
import Main from "../../components/Main";
import Header from "../../components/styled/Header";
import Page from "../../components/Page";
import {useState} from "react";
import TextField from "../../components/TextField";
import Button from "../../components/styled/Button";
import Error from "../../components/Error";
import Loading from "../../components/Loading";
import EachUserMapper from "../../components/EachUserMapper";
import {getNewVideo} from "../../service/apiService";



export default function Admin() {
    const {user, token} = useAuth()
    const [newVideoId, setNewVideoId] = useState("")
    const [loading, setLoading] = useState(false)
    const [fetchedVideo, setFetchedVideo] = useState({})
    const [error, setError] = useState()

    const handleSubmitUpload = (event) => {
        event.preventDefault()
        setLoading(true)
        getNewVideo(newVideoId, token)
            .then(fetchedVideo => setFetchedVideo(fetchedVideo))
            .catch(error => setError(error),
                setLoading(false))
            .finally(() => setNewVideoId(""))
    }

    console.log(fetchedVideo.items)


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
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>
    )

}