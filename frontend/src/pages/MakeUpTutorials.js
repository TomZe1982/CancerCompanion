import NavBar from "../components/NavBar";
import Header from "../components/Header";
import Page from "../components/Page";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Redirect} from "react-router-dom";
import List from "../components/List";
import VideoGallery from "../components/VideoGallery";
import {getVideoList} from "../service/apiService";
import {useEffect, useState} from "react";



export default function MakeUpTutorials() {
    const {user, token} = useAuth()

    const [videoList, setVideoList] = useState([])


    useEffect(()=>{
        getVideoList(token)
            .then(setVideoList)
    }, [token])



    const newVideoList = videoList.map(video => (
        <VideoGallery videoId = {video.vid_id} key = {video.id}
        />)
    )


    if(!user){
        return <Redirect to = "/login"/>
    }


    return (
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title="Schmink - Turorials"/>
                <ul>
                    <List>{newVideoList}</List>
                </ul>
            </Main>
        </Page>
    )

}