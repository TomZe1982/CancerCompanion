import BlogImage from "./BlogImage";
import {useEffect, useState} from "react";
import {getBlogList} from "../../service/apiService";
import {useAuth} from "../../auth/AuthProvider";
import StyledLinkBlog from "../styled/StyledLinkBlog";




export default function BlogCard({fetchedUserNameForBlog, avatar}) {
    const {token} = useAuth()
    const [allBlogs, setAllBlogs] = useState([])

    useEffect(() => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
            .catch(error => console.error(error))
    }, [fetchedUserNameForBlog, token])


    return (
        <div>
            {(allBlogs.length > 0 ) ?
                (<StyledLinkBlog to={`/userblogs/${fetchedUserNameForBlog}`}>
                    <BlogImage className="user__image" src={avatar} alt="userImage"/>
                    <h4 className="user__name">{fetchedUserNameForBlog}</h4>
                </StyledLinkBlog>)
                : null}
        </div>
    )
}
