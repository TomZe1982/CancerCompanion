import {useParams} from "react-router-dom";
import Page from "../../components/Page";
import NavBar from "../../components/NavBar";
import Main from "../../components/Main";
import {useAuth} from "../../auth/AuthProvider";
import {useEffect, useState} from "react";
import {getInfoById, updateInfo} from "../../service/apiService";
import TextArea from "../../components/TextArea";
import Button from "../../components/styled/Button";
import Error from "../../components/Error";
import TextAreaUpdate from "../../components/TextAreaUpdate";



export default function UpdateEachInformation(){
    const {user, token} = useAuth()
    const {informationId} = useParams();
    const [error, setError] = useState()
    const [updatedInfo, setUpdatedInfo] = useState({})

    useEffect(()=>{
        getInfoById(informationId, token)
            .then(setUpdatedInfo)
            .catch(error => setError(error))

    }, [informationId, token])

    const handleChange = (event) => {
        setUpdatedInfo({...updatedInfo, [event.target.name]: event.target.value})
    }

    const reloadPage = () => {
        getInfoById(informationId, token)
            .then(setUpdatedInfo)
            .catch(error => setError(error))
    }

    const handleClick = () => {
        updateInfo(informationId, updatedInfo, token)
            .then(setUpdatedInfo)
            .then(reloadPage)
            .catch(error => setError(error))
    }

    console.log(updatedInfo)

    return (
        <Page>
            <NavBar user = { user } />
            <Main>
                <TextArea
                    title="Info bearbeiten"
                    name="title"
                    value={updatedInfo.title}
                    onChange={handleChange}
                />
                <TextAreaUpdate
                    title="Info bearbeiten"
                    name="info"
                    value={updatedInfo.info}
                    onChange={handleChange}
                />
                <Button onClick = { handleClick }>Bestätigen</Button>
                {error && <Error>{ error.response.data.error}</Error>}
            </Main>
        </Page>
    )
}