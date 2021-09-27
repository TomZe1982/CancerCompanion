import {deleteInfo, getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthProvider";
import Button from "../../components/styled/Button";
import NavBar from "../../components/NavBar";
import Main from "../../components/Main";
import Page from "../../components/Page";
import {Link} from "react-router-dom";
import Box from "../../components/styled/Box";
import InnerBox from "../../components/styled/InnerBox";


export default function UpdateInformation(){
    const {user, token} = useAuth()
    const [infos, setInfos] = useState([])


    useEffect(() => {
        getInfos()
            .then(setInfos)
            .catch(error => console.error(error))
    }, [])


    function reloadPage() {
        return getInfos()
            .then(setInfos)
    }

    const info = infos.map(infoDetails =>
        <section key = {infoDetails.id} >
            <Box>
                <InnerBox>
        <section>{infoDetails.title}</section>
                </InnerBox>
            <Button onClick={() => deleteInfo(infoDetails.id, token).then(reloadPage)}>Info löschen</Button>
            <Link to = {`/updateeachinfo/${infoDetails.id}`} >Info bearbeiten</Link>
            </Box>
        </section>
   )


    return(
        <Page>
            <NavBar user = { user } />
            <Main>
                {info}
            </Main>
        </Page>
    )


}