import {useAuth} from "../../../auth/AuthProvider";
import {useState} from "react";
import {postBlogEntry} from "../../../service/apiService";
import TextArea from "../../../components/TextArea";
import Button from "../../../components/styled/Button";
import Main from "../../../components/Main";
import Page from "../../../components/Page";
import Header from "../../../components/styled/Header";
import {useHistory} from "react-router-dom";
import NavBar from "../../../components/NavBar";


export default function NewBlog() {
    const {user, token} = useAuth()
    const [blogEntry, setBlogEntry] = useState({})
    const history = useHistory()


    const handleSubmit = (event) => {
        event.preventDefault()
        postBlogEntry(blogEntry, token)
            .then(blogEntry => setBlogEntry(blogEntry))
            .then(redirectHandler)
            .catch(error => console.error(error))
            .finally(() => setBlogEntry({blogEntry: ""}))
    }

    const handleOnChange = (event) => {
        setBlogEntry({[event.target.name]: event.target.value})
    }

    const redirectHandler = () => {
         history.push(`/userblogs/${user.userName}`)
    }

    return (
        <Page>
            <NavBar user = { user } />
            <Main as = "form" onSubmit={handleSubmit}>
                <Header title = "Neuer Blog" />
                <section>
                    <TextArea
                        title="Neuer Blog Eintrag"
                        name="entry"
                        value={blogEntry.entry || ""}
                        onChange={handleOnChange}
                    />
                    <Button>Abschicken</Button>
                </section>
            </Main>
        </Page>


    )


}