import {useEffect, useState} from "react";
import {createInbox} from "../service/apiService";


export default function EmailHandler(){
    const [inbox, setInbox] = useState()

    useEffect(() => {
        createInbox()
            .then(response => setInbox(response))
    }, [])

    console.log(inbox)


    return(
        <div>
            <section>{inbox}</section>
        </div>
    )
}