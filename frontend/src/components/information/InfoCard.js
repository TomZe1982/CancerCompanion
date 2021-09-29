import {getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import StyledLinkInfo from "../styled/StyledLinkInfo";
import styled from "styled-components/macro";



export default function InfoCard() {
    const [infos, setInfos] = useState([])

    useEffect(() => {
        getInfos()
            .then(setInfos)
            .catch(error => console.error(error))
    }, [])




    const infoMap = infos.map(infoDetails => <StyledLinkInfo key={infoDetails.id} as={Link}
                                                         to={`/infodetails/${infoDetails.id}`}>{infoDetails.title}

        </StyledLinkInfo>
    )

    return (
        <Wrapper>
            {infoMap}
        </Wrapper>
    )

}


const Wrapper = styled.div`
width: 100%;
  
`