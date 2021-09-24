import {useAuth} from "../../auth/AuthProvider";
import { getBlogList, postBlogEntry} from "../../service/apiService";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import Box from "../Box";
import TextField from "../TextField";
import Button from "../Button";


export default function BlogEntries ( ){

    const {token, user} = useAuth()
    const {fetchedUserNameForBlog} = useParams()
    const [allBlogs, setAllBlogs] = useState([])
    const [blogEntry, setBlogEntry] = useState({})

    console.log(blogEntry)

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

    const blog = allBlogs.map(blog =>   <Box><section>{blog.date} {blog.entry}</section> </Box>)

    return (
        <div>
            <section>
                <p>{blog}</p>
            </section>
            {user.userName === fetchedUserNameForBlog &&
            <div>
                <TextField
                    title = "entry"
                    name = "entry"
                    value = {blogEntry.entry || ""}
                    onChange = {handleOnChange}
                />
                <Button onClick={handleSubmit}>Abschicken</Button>
            </div>
                }
        </div>
    )

}