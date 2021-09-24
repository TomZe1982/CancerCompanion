import Page from "../components/Page";
import Header from "../components/Header";
import Main from "../components/Main";
import {useAuth} from "../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import NavBar from "../components/NavBar";
import UserImage from "../components/UserImage";
import ProfileButton from "../components/ProfileButton";




export default function Profile(){
    const {user} = useAuth()

    if(!user){
   return  <Redirect to = "/login"/>
    }

    return(
        <Page>
            <NavBar user = {user}/>
            <Main>
                <Header title ={user.userName} />
                <UserImage src = "https://thispersondoesnotexist.com/image" alt = "userImage"/>
                <ProfileButton as = {Link} to = "/editsettings">Email ändern</ProfileButton>
                <ProfileButton as = {Link} to = "/editpassword">Passwort ändern</ProfileButton>
                <ProfileButton as = {Link} to = "/delete" >Profil löschen</ProfileButton>
                <ProfileButton as = {Link} to = "/tutorials">Schmink Tutorials</ProfileButton>
                <ProfileButton as = {Link} to = "/userblogs">Blog</ProfileButton>
                <ProfileButton as = {Link} to = "/logout">Logout</ProfileButton>
            </Main>
        </Page>
    )
}