
import {useEffect, useState} from "react";
import {Redirect, useParams} from "react-router-dom";
import {getInfoById} from "../../service/apiService";


export default function EachInfo(){

    const {infoDetails} = useParams()
    const [info, setInfo] = useState({})

    useEffect(() => {
        getInfoById(infoDetails)
            .then(setInfo)
            .catch(error => console.error(error))
    }, [infoDetails])

    if(!infoDetails){
        return <Redirect to = "/info" />
    }


    return (
        <div>
            <section>
                {info.info}
            </section>
        </div>
    )

}