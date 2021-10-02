import Page from "../../components/Page";
import Header from "../../components/styled/Header";
import Main from "../../components/Main";
import {useAuth} from "../../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import NavBar from "../../components/NavBar";
import UserImage from "../../components/UserImage";
import ProfileButton from "../../components/styled/ProfileButton";
import {useEffect, useState} from "react";
import {getBlogList} from "../../service/apiService";
import Error from "../../components/Error";
import Loading from "../../components/Loading";




export default function Profile(){
    const {user, token} = useAuth()
    const [blogs, setBlogs] = useState([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()


    useEffect(() => {
        setLoading(true)
        getBlogList(user.userName, token)
            .then(setBlogs)
            .then(()=> setLoading(false))
            .catch(error => setError(error))

    }, [user, token])

    if(!user){
   return  <Redirect to = "/login"/>
    }



    return(
        <Page>
            <NavBar user = {user}/>
            {loading && <Loading/>}
            {!loading &&(
            <Main>
                <Header title = {user.userName} />
                <UserImage src ={user.avatar || "NA.png"} alt = "user_image"/>
                <ProfileButton as = {Link} to = "/editsettings">Email ändern</ProfileButton>
                <ProfileButton as = {Link} to = "/editpassword">Passwort ändern</ProfileButton>
                <ProfileButton as = {Link} to = "/delete" >Profil löschen</ProfileButton>
                <ProfileButton as = {Link} to = "/tutorials">Schmink Tutorials</ProfileButton>
                <ProfileButton as = {Link} to = "/logout">Logout</ProfileButton>
                {!blogs.length > 0 && <ProfileButton as = {Link} to = "/newBlog">Bloggen</ProfileButton>}
            </Main>
            )}
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>
    )
}