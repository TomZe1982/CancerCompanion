import {getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import StyledLinkInfo from "../styled/StyledLinkInfo";
import styled from "styled-components/macro";
import Error from "../Error";



export default function InfoCard() {
    const [infos, setInfos] = useState([])
    const [error, setError] = useState()

    useEffect(() => {
        getInfos()
            .then(setInfos)
            .catch(error => setError(error))
    }, [])




    const infoMap = infos.map(infoDetails => <StyledLinkInfo key={infoDetails.id} as={Link}
                                                         to={`/infodetails/${infoDetails.id}`}>{infoDetails.title}

        </StyledLinkInfo>
    )

    return (
        <Wrapper>
           <p>{infoMap}</p>
            {error && <Error>{ error.response.data.error}</Error>}
        </Wrapper>
    )

}


const Wrapper = styled.div`
width: 100%;
  
`