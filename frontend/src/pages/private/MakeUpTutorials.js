import NavBar from "../../components/NavBar";
import Header from "../../components/styled/Header";
import Page from "../../components/Page";
import Main from "../../components/Main";
import {useAuth} from "../../auth/AuthProvider";
import {Redirect} from "react-router-dom";
import List from "../../components/List";
import {getVideoList} from "../../service/apiService";
import { useEffect, useState} from "react";
import VideoCard from "../../components/video/VideoCard";



export default function MakeUpTutorials() {
    const {user, token} = useAuth()

    const [videoList, setVideoList] = useState([])


    useEffect(()=>{
        getVideoList(token)
            .then(setVideoList)
    }, [user, token])


    const reloadPage = () =>{
        getVideoList(token)
            .then(setVideoList)
    }




    const newVideoList = videoList.map(video => (
        <VideoCard videoId = {video.vid_id} key = {video.id}
                      reloadPage = {reloadPage}
        />)
    )


    if(!user){
        return <Redirect to = "/login"/>
    }


    return (
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title="Tutorials"/>
                <ul>
                    <List>{newVideoList}</List>
                </ul>
            </Main>
        </Page>
    )

}