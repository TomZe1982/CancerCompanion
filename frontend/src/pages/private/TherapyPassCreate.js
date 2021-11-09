import NavBar from "../../components/NavBar";
import {useAuth} from "../../auth/AuthProvider";
import Page from "../../components/Page";
import Main from "../../components/Main";
import Header from "../../components/styled/Header";
import DateTimePicker from 'react-datetime-picker';
import { useState } from "react";
import {createTherapy} from "../../service/apiService";
import Error from "../../components/Error";
import Loading from "../../components/Loading";
import TextField from "../../components/TextField";
import Button from "../../components/styled/Button";
import TextArea from "../../components/TextArea";
import {useParams} from "react-router-dom";


export default function TherapyPassCreate() {
    const { fetchedUserName } = useParams()
    const {user, token} = useAuth()
    const [therapy, setTherapy] = useState({})
    const [error, setError] = useState()
    const [loading, setLoading] = useState(false)
    const [date, setDate] = useState(new Date())


    const handleOnChangeCreate = (event) => {
        setTherapy({...therapy, date, [event.target.name]: event.target.value})
    }

    const handleDelete = () => {
        setTherapy({})
    }

    const handleSubmitCreate = (event) => {
        setLoading(true)
        event.preventDefault()
        createTherapy(fetchedUserName, therapy, token)
            .then(() => setTherapy({}))
            .then(() => setLoading(false))
            .catch(error => setError(error))
    }



    console.log(therapy)

    return (
        <Page>
            <NavBar user={user}/>
            {loading && <Loading/>}
            {!loading &&
            (
                <Main>
                    <Header title="Therapie-Pass"/>

                    <div>
                        <DateTimePicker name="date" value={date} onChange={setDate}/>
                    </div>
                    <form onSubmit={handleSubmitCreate}>
                        <Header title={fetchedUserName}/>
                        <p>Neuer Therapie-Termin</p>
                        <TextField
                            title="Therapy Art"
                            name="title"
                            value={therapy.title || ""}
                            onChange={handleOnChangeCreate}/>
                        <TextArea
                            title="Therapy Beschreibung"
                            name="description"
                            value={therapy.description || ""}
                            onChange={handleOnChangeCreate}/>
                        {(therapy.description !== "" && therapy.title !== "") ?
                            <Button>Bestätigen</Button> : <Error>Bitte Felder befüllen</Error>}
                    </form>
                    <Button onClick={handleDelete}>Abbrechen</Button>

                </Main>
            )}
            {error && <Error>{error.response.data.error}</Error>}
        </Page>


    )
}