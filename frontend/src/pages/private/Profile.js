import Page from "../../components/Page";
import Header from "../../components/styled/Header";
import Main from "../../components/Main";
import {useAuth} from "../../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import NavBar from "../../components/NavBar";
import UserImage from "../../components/UserImage";
import ProfileButton from "../../components/ProfileButton";
import {useEffect, useState} from "react";
import {getBlogList} from "../../service/apiService";




export default function Profile(){
    const {user, token} = useAuth()
    const [blogs, setBlogs] = useState([])

    useEffect(() => {
        getBlogList(user.userName, token)
            .then(setBlogs)
            .catch(error => console.error(error))

    }, [user, token])

    if(!user){
   return  <Redirect to = "/login"/>
    }



    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title = {user.userName} />
                <UserImage src = "https://thispersondoesnotexist.com/image" alt = "userImage"/>
                <ProfileButton as = {Link} to = "/editsettings">Email ändern</ProfileButton>
                <ProfileButton as = {Link} to = "/editpassword">Passwort ändern</ProfileButton>
                <ProfileButton as = {Link} to = "/delete" >Profil löschen</ProfileButton>
                <ProfileButton as = {Link} to = "/tutorials">Schmink Tutorials</ProfileButton>
                <ProfileButton as = {Link} to = "/logout">Logout</ProfileButton>
                {!blogs.length > 0 && <ProfileButton as = {Link} to = "/newBlog">Bloggen</ProfileButton>}
            </Main>
        </Page>
    )
}