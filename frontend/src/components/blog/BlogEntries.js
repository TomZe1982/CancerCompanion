import {useAuth} from "../../auth/AuthProvider";
import {deleteBlogEntry, getBlogList, postBlogEntry} from "../../service/apiService";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import Box from "../styled/Box";
import TextArea from "../TextArea";
import Button from "../styled/Button";
import InnerBox from "../styled/InnerBox";
import Error from "../Error";
import styled from "styled-components/macro";



export default function BlogEntries() {

    const {token, user} = useAuth()
    const {fetchedUserNameForBlog} = useParams()
    const [allBlogs, setAllBlogs] = useState([])
    const [blogEntry, setBlogEntry] = useState({})
    const [error, setError] = useState()

    console.log(allBlogs)

    useEffect(() => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
            .catch(error => setError(error))
    }, [fetchedUserNameForBlog, token])

    const handleSubmit = () => {
        postBlogEntry(blogEntry, token)
            .then(blogEntry => setBlogEntry(blogEntry))
            .then(reloadBlogPage)
            .catch(error => setError(error))
            .finally(() => setBlogEntry({blogEntry: ""}))
    }

    const reloadBlogPage = () => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
    }

    const handleOnChange = (event) => {
        setBlogEntry({[event.target.name]: event.target.value})
    }

    console.log(allBlogs)


    const blog = allBlogs.map(blog =>
        <Box key={blog.blogId}>
            <InnerBox>
                <section>
                    <Date>{blog.date}</Date>
                </section>
                <section>
                    <h4>{blog.entry}</h4>
                </section>
            </InnerBox>
            <section>
                {(user.role === "admin" || user.userName === fetchedUserNameForBlog) &&
                <Button
                    onClick={blog.blogId ? (() => deleteBlogEntry(fetchedUserNameForBlog, blog.blogId, token).then(reloadBlogPage))
                        : console.error("error")}>Blog löschen</Button>}
            </section>
        </Box>)


    return (
        <div>
                {blog}

            {user.userName === fetchedUserNameForBlog &&
            <section>
                <TextArea
                    title="Neuer Blog Eintrag"
                    name="entry"
                    value={blogEntry.entry || ""}
                    onChange={handleOnChange}
                />
                <Button onClick={handleSubmit}>Abschicken</Button>
            </section>
            }
            {error && <Error>{ error.response.data.error}</Error>}
        </div>
    )

}

const Date = styled.p`
text-align: center;
  color: dimgrey;
`
