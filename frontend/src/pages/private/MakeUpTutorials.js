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
import Error from "../../components/Error";
import Loading from "../../components/Loading";



export default function MakeUpTutorials() {
    const {user, token} = useAuth()

    const [videoList, setVideoList] = useState([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()

    useEffect(()=>{
        setLoading(true)
        getVideoList(token)
            .then(setVideoList)
            .then(() => setLoading(false))
            .catch(error => setError(error))
    }, [user, token])


    const reloadPage = () =>{
        setLoading(false)
        getVideoList(token)
            .then(setVideoList)
            .then(()=>setLoading(false))
            .catch(error => setError(error))
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
            {loading && <Loading/>}
            {!loading && (
            <Main>
                <Header title="Tutorials"/>
                <ul>
                    <List>{newVideoList}</List>
                </ul>
            </Main>
            )}
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>
    )

}