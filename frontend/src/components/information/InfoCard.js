import {getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import StyledLink from "../styled/StyledLink";



export default function InfoCard() {
    const [infos, setInfos] = useState([])

    useEffect(() => {
        getInfos()
            .then(setInfos)
            .catch(error => console.error(error))
    }, [])




    const infoMap = infos.map(infoDetails => <StyledLink key={infoDetails.id} as={Link}
                                                         to={`/infodetails/${infoDetails.id}`}>{infoDetails.title}

        </StyledLink>
    )

    return (
        <div>
            {infoMap}
        </div>
    )

}


//{ user.role === "admin" && <section><Button onClick={() => deleteInfo(infoDetails.id, token)}>Info löschen</Button></section>}