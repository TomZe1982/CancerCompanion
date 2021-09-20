import {useEffect, useState} from "react";
import {getBlogList} from "../service/apiService";
import {useAuth} from "../auth/AuthProvider";


export default function BlogCard ({fetchedUserNameForBlog}) {
    const {token} = useAuth()
    const [allBlogs, setAllBlogs] = useState([])

    useEffect(() => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
            .catch(error => console.error(error))
    }, [fetchedUserNameForBlog, token])

    const blogs = allBlogs.map(blog => (blog.date && blog.entry))

    console.log(allBlogs)



    return (<div>
        <section>
            <h4>{fetchedUserNameForBlog} : </h4>
            {blogs}
        </section>
    </div>
    )

}