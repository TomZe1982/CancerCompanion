import { useHistory } from "react-router-dom";
import Button from "../Button";
import BlogImage from "./BlogImage";
import Section from "../styled/Section";
import {useEffect, useState} from "react";
import {getBlogList} from "../../service/apiService";
import {useAuth} from "../../auth/AuthProvider";


export default function BlogCard( { fetchedUserNameForBlog } ) {
    const{ token } = useAuth()
    const history = useHistory()
    const [allBlogs, setAllBlogs] = useState([])

    useEffect(() => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
            .catch(error => console.error(error))
    }, [fetchedUserNameForBlog, token])

    function handleClickReadBlog() {
        history.push(`/userblogs/${fetchedUserNameForBlog}`)
    }


        return (
            <div>
                {( allBlogs !== [] ) ?
                    ( <Section>
                    <BlogImage className="user__image" src="https://thispersondoesnotexist.com/image" alt="userImage"/>
                    <h4 className="user__name">{fetchedUserNameForBlog}</h4>
                    <Button className="button" onClick={handleClickReadBlog}>Blog lesen</Button>
                </Section>)
                   : null}
            </div>
        )
}
