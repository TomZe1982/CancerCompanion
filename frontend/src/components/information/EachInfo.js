
import {useEffect, useState} from "react";
import {Redirect, useParams} from "react-router-dom";
import {getInfoById} from "../../service/apiService";


export default function EachInfo(){

    const {infoId} = useParams()
    const [info, setInfo] = useState({})

    useEffect(() => {
        getInfoById(infoId)
            .then(setInfo)
            .catch(error => console.error(error))
    }, [infoId])

    if(!infoId){
        return <Redirect to = "/info" />
    }


    return (
        <div>
            <section>
                <h2>{info.title}</h2>
            </section>
            <section>
                <p>{info.info}</p>
            </section>
        </div>
    )

}