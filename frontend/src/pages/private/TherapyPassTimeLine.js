import Page from "../../components/Page";
import Header from "../../components/styled/Header";
import Main from "../../components/Main";
import {useAuth} from "../../auth/AuthProvider";
import {useEffect, useState} from "react";
import Loading from "../../components/Loading";
import Error from "../../components/Error";
import {getTherapies} from "../../service/apiService";
import {useParams} from "react-router-dom";
import NavBar from "../../components/NavBar";
import TimeLine from "../../components/TherapyPass/TimeLine";


export default function TherapyPassTimeLine() {
    const { user, token } = useAuth()
    const { fetchedUserName } = useParams()
    const [therapyList, setTherapyList] = useState([])
    const [error, setError] = useState()
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        setLoading(true)
        getTherapies(fetchedUserName, token)
            .then(therapyList => setTherapyList(therapyList))
            .then(() => setLoading(false))
            .catch(error => setError(error))

    }, [fetchedUserName, token])

    const reloadPage = () => {
        setLoading(true)
        getTherapies(fetchedUserName, token)
            .then(therapyList => setTherapyList(therapyList))
            .then(() => setLoading(false))
            .catch(error => setError(error))
    }

    console.log(therapyList)

    return (
        <Page>
            <NavBar user={user}/>
            {loading && <Loading/>}
            {!loading && (
            <Main>
                <Header title="Therapy Pass"/>

               <TimeLine reloadPage={reloadPage} fetchedUserName = {fetchedUserName} therapyList = { therapyList }/>
            </Main>
            )}
            {error && <Error>{error.response.data.error}</Error>}
        </Page>
    )
}