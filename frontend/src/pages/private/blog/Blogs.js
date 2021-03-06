import Main from "../../../components/Main";
import Header from "../../../components/styled/Header";
import NavBar from "../../../components/NavBar";
import Page from "../../../components/Page";
import {useAuth} from "../../../auth/AuthProvider";
import EachUserMapperBlog from "../../../components/blog/EachUserMapperBlog";


export default function Blogs() {
    const {user} = useAuth()


    return (
        <Page>
            <NavBar user={user}/>
            <Main>
                <Header title="Blogs"/>
                <EachUserMapperBlog/>
            </Main>
        </Page>
    )
}