import {getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import StyledLinkInfo from "../styled/StyledLinkInfo";
import styled from "styled-components/macro";
import Error from "../Error";
import Loading from "../Loading";



export default function InfoCard() {
    const [infos, setInfos] = useState([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()

    useEffect(() => {
        setLoading(true)
        getInfos()
            .then(setInfos)
            .then(loading => setLoading(loading === false))
            .catch(error => setError(error))
    }, [])




    const infoMap = infos.map(infoDetails => <StyledLinkInfo key={infoDetails.id} as={Link}
                                                         to={`/infodetails/${infoDetails.id}`}>{infoDetails.title}

        </StyledLinkInfo>
    )

    return (
        <Wrapper>
            {loading && <Loading/>}
            {!loading && (
           <p>{infoMap}</p>
            )}
            {error && <Error>{ error.response.data.error}</Error>}
        </Wrapper>
    )

}


const Wrapper = styled.div`
width: 100%;
`