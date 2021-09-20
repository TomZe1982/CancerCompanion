import {useEffect, useState} from "react";
import {getBlogEntry, getBlogList} from "../service/apiService";
import {useAuth} from "../auth/AuthProvider";


export default function BlogCard ({fetchedUserNameForBlog, reloadUserPage}) {
    const {token} = useAuth()
    const [allBlogs, setAllBlogs] = useState([])

    useEffect(() => {
        getBlogList(fetchedUserNameForBlog, token)
            .then(setAllBlogs)
            .catch(error => console.error(error))
    }, [getBlogList, token])

    const blogs = allBlogs.map(blog => (blog.date , blog.entry))

    console.log(allBlogs)



    return (<div>
        <section>
            {fetchedUserNameForBlog}
            {blogs}
        </section>
    </div>
    )

}