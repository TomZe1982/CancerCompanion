import Header from "../../components/styled/Header";
import Page from "../../components/Page";
import Main from "../../components/Main";
import {useAuth} from "../../auth/AuthProvider";
import {Link, Redirect} from "react-router-dom";
import Button from "../../components/styled/Button";
import {useState} from "react";
import NavBar from "../../components/NavBar";
import Loading from "../../components/Loading";
import {deleteUser} from "../../service/apiService";
import Error from "../../components/Error";



export default function Delete() {
    const {user, token, logout} = useAuth()
    const [deletedUser, setDeletedUser] = useState()
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState()

    if(user.role === "admin"){
        return <Redirect to = "/adminerror"/>
    }

    const handleDelete = () => {
        setLoading(true)
        deleteUser(user.userName, token)
            .then(deletedUser => setDeletedUser(deletedUser))
            .then(logout)
            .then(() => setLoading(false))
            .catch(error => setError(error))
    }


    if(deletedUser)
    {
        return <Redirect to = "/"/>
    }

    return (
        <Page>
            <NavBar user = {user}/>
            <Header title="Löschen"/>
            {loading && <Loading/>}
            {!loading && (
                <Main>
                <p>{user.userName}, möchtest Du Dein Profil wirklich löschen?</p>
                <Button as = {Link} to = "/profile">Nein, natürlich nicht</Button>
                <Button as = {Link} to = "/" onClick = {handleDelete}>Ja</Button>
            </Main>
                )}
            {error && <Error>{ error.response.data.error}</Error>}
        </Page>
    )
}