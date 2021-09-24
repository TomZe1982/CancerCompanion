import BlogImage from "./BlogImage";
import {useEffect, useState} from "react";
import {getBlogList} from "../../service/apiService";
import {useAuth} from "../../auth/AuthProvider";
import StyledLink from "../styled/StyledLink";



export default function BlogCard({fetchedUserNameForBlog}) {
    const {token} = useAuth()
    const [allBlogs, setAllBlogs] = useState([])

    useEffect(() => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
            .catch(error => console.error(error))
    }, [fetchedUserNameForBlog, token])


    return (<div>
            {(allBlogs.length > 0) ?
                (<StyledLink to={`/userblogs/${fetchedUserNameForBlog}`}>
                    <BlogImage className="user__image" src="https://thispersondoesnotexist.com/image" alt="userImage"/>
                    <h4 className="user__name">{fetchedUserNameForBlog}</h4>
                </StyledLink>)
                : null}
        </div>
    )
}
