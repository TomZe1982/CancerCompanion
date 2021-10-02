
import {useEffect, useState} from "react";
import {Redirect, useParams} from "react-router-dom";
import {getInfoById} from "../../service/apiService";
import Box from "../styled/Box";
import Error from "../Error";


export default function EachInfo(){

    const {infoId} = useParams()
    const [info, setInfo] = useState({})
    const [error, setError] = useState()

    useEffect(() => {
        getInfoById(infoId)
            .then(setInfo)
            .catch(error => setError(error))
    }, [infoId])

    if(!infoId){
        return <Redirect to = "/info" />
    }


    return (
        <div>
            <Box>
            <section>
                <h3>{info.title}</h3>
            </section>
            <section>
                <p>{info.info}</p>
            </section>
            </Box>
            {error && <Error>{ error.response.data.error}</Error>}
        </div>
    )

}