import {deleteInfo, getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthProvider";
import Button from "../../components/styled/Button";
import NavBar from "../../components/NavBar";
import Main from "../../components/Main";
import Page from "../../components/Page";
import {Link} from "react-router-dom";
import UpdateBox from "../../components/styled/UpdateBox";
import Error from "../../components/Error";



export default function UpdateInformation(){
    const {user, token} = useAuth()
    const [infos, setInfos] = useState([])
    const [error, setError] = useState()

    useEffect(() => {
        getInfos()
            .then(setInfos)
            .catch(error => setError(error))
    }, [])


    function reloadPage() {
        return getInfos()
            .then(setInfos)
    }

    const info = infos.map(infoDetails =>

            <UpdateBox key = {infoDetails.id}>
        <section className = "details">{infoDetails.title}</section>
            <Button className = "button" onClick={() => deleteInfo(infoDetails.id, token).then(reloadPage)}>Info löschen</Button>
            <Link className = "link" to = {`/updateeachinfo/${infoDetails.id}`} >Info bearbeiten</Link>
            </UpdateBox>

   )


    return(
        <Page>
            <NavBar user = { user } />
            <Main>
                {info}
            </Main>
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>
    )

}

