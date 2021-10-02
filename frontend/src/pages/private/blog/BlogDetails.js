import NavBar from "../../../components/NavBar";
import {useAuth} from "../../../auth/AuthProvider";
import Page from "../../../components/Page";
import Main from "../../../components/Main";
import Header from "../../../components/styled/Header";
import BlogEntries from "../../../components/blog/BlogEntries";
import {useParams} from "react-router-dom";

export default function BlogDetails() {
    const {user} = useAuth()
    const {fetchedNameForUserBlog} = useParams()


    return (
        <Page>
            <NavBar user={user}/>
            <Main>
                <Header title={fetchedNameForUserBlog}/>
                <BlogEntries/>
            </Main>

        </Page>
    )


}