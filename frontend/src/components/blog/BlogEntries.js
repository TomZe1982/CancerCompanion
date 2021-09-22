import {useAuth} from "../../auth/AuthProvider";
import {deleteBlogEntry, getBlogList, postBlogEntry} from "../../service/apiService";
import {useEffect, useState} from "react";
import {Link, useParams} from "react-router-dom";
import Box from "../styled/Box";
import TextField from "../TextField";
import Button from "../Button";
import InnerBox from "../styled/InnerBox";
import BlogSection from "../styled/BlogSection";



export default function BlogEntries ( ){

    const {token, user} = useAuth()
    const {fetchedUserNameForBlog} = useParams()
    const [allBlogs, setAllBlogs] = useState([])
    const [blogEntry, setBlogEntry] = useState({})

    console.log(allBlogs)

    useEffect(() => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
            .catch(error => console.error(error))
    }, [fetchedUserNameForBlog, token])

    const handleSubmit = () => {
        postBlogEntry(blogEntry, token)
            .then(blogEntry => setBlogEntry(blogEntry))
            .then(reloadBlogPage)
            .catch(error => console.error(error))
            .finally(() => setBlogEntry({blogEntry: ""}))
    }

    const reloadBlogPage = () => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
    }

    const handleOnChange = (event) => {
        setBlogEntry({[event.target.name] : event.target.value})
    }

    const blog = allBlogs.map(blog =>
        <Box><InnerBox><BlogSection>{blog.date} {blog.entry}</BlogSection></InnerBox>
            <section>
        {(user.role === "admin" || user.userName === fetchedUserNameForBlog) && <Button onClick = {() => deleteBlogEntry(blog.blogId, token)
            .then(reloadBlogPage)} >Blog löschen</Button>}
        </section> </Box>)

    return (
        <div>
            <section>
                <p>{blog}</p>

            </section>
            {user.userName === fetchedUserNameForBlog &&
            <section>
                <TextField
                    title = "Neuer Blog Eintrag"
                    name = "entry"
                    value = {blogEntry.entry || ""}
                    onChange = {handleOnChange}
                />
                <Button onClick={handleSubmit}>Abschicken</Button>
            </section>
                }
        </div>
    )

}