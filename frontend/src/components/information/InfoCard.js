import {deleteInfo, getInfos} from "../../service/apiService";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import StyledLink from "../styled/StyledLink";
import {useAuth} from "../../auth/AuthProvider";
import Button from "../styled/Button";


export default function InfoCard() {
    const {user, token} = useAuth()
    const [infos, setInfos] = useState([])

    useEffect(() => {
        getInfos()
            .then(setInfos)
            .catch(error => console.error(error))
    }, [])



    const infoMap = infos.map(infoDetails => <StyledLink key={infoDetails.id} as={Link}
                                                         to={`/infodetails/${infoDetails.id}`}>{infoDetails.title}
        <section>
    {user.role === "admin" && <Button onClick={() => deleteInfo(infoDetails.id, token)}>Info löschen</Button>}
        </section>
        </StyledLink>
    )


    return (
        <div>
            {infoMap}
        </div>
    )

}

