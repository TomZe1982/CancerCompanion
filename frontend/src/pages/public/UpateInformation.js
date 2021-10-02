import {deleteInfo, getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthProvider";
import Button from "../../components/styled/Button";
import NavBar from "../../components/NavBar";
import Main from "../../components/Main";
import Page from "../../components/Page";
import UpdateBox from "../../components/styled/UpdateBox";
import Error from "../../components/Error";
import styled from "styled-components/macro";
import {Link} from "react-router-dom";



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
                <details>
            <Button className = "button" onClick={() => deleteInfo(infoDetails.id, token).then(reloadPage)}>Info löschen</Button>
            <UpdateLink className = "link" to = {`/updateeachinfo/${infoDetails.id}`} >Info bearbeiten</UpdateLink>
                </details>
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

const UpdateLink = styled(Link)`
  display: inline-block;
  width: 150px;
  text-align: center;
  padding: var(--size-xs);
  margin: 5px 5px;
  background: var(--accent);
  border: 1px solid var(--accent);
  color: var(--neutral-light);
  font-size: 1em;
  border-radius: 5px;
    
`