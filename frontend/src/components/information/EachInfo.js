
import {useEffect, useState} from "react";
import {Redirect, useParams} from "react-router-dom";
import {getInfoById} from "../../service/apiService";
import Box from "../styled/Box";
import Error from "../Error";
import Loading from "../Loading";


export default function EachInfo(){

    const {infoId} = useParams()
    const [info, setInfo] = useState({})
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()

    useEffect(() => {
        setLoading(true)
        getInfoById(infoId)
            .then(setInfo)
            .then(() => setLoading(false))
            .catch(error => setError(error))
    }, [infoId])

    if(!infoId){
        return <Redirect to = "/info" />
    }


    return (
        <div>
            {loading && <Loading/>}
            {!loading && (
            <Box>
            <section>
                <h3>{info.title}</h3>
            </section>
            <section>
                <p>{info.info}</p>
            </section>
            </Box>
            )}
            {error && <Error>{ error.response.data.error}</Error>}
        </div>
    )

}