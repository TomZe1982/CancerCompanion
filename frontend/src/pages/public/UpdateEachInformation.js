import {useHistory, useParams} from "react-router-dom";
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
import Loading from "../../components/Loading";



export default function UpdateEachInformation(){
    const history = useHistory()
    const {user, token} = useAuth()
    const {informationId} = useParams();
    const [error, setError] = useState()
    const [loading, setLoading] = useState(false)
    const [updatedInfo, setUpdatedInfo] = useState({})

    useEffect(()=>{
        setLoading(true)
        getInfoById(informationId, token)
            .then(setUpdatedInfo)
            .then(loading => setLoading(loading === false))
            .catch(error => setError(error))

    }, [informationId, token])

    const handleChange = (event) => {
        setUpdatedInfo({...updatedInfo, [event.target.name]: event.target.value})
    }

    const redirectTo = () => {
        return history.push("/info")
    }

    const handleClick = () => {
        setLoading(true)
        updateInfo(informationId, updatedInfo, token)
            .then(setUpdatedInfo)
            .then(redirectTo)
            .catch(error => setError(error))

    }

    console.log(updatedInfo)

    return (
        <Page>
            <NavBar user = { user } />
            {loading && <Loading/>}
            {!loading && (
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
                <Button onClick={handleClick}>Bestätigen</Button>
                {error && <Error>{error.response.data.error}</Error>}
            </Main>
            )}
        </Page>
    )
}