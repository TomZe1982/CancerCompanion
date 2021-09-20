import {useEffect, useState} from "react";
import {useAuth} from "../auth/AuthProvider";
import BlogGallery from "./BlogGallery";



export default function EachUserMapperBlog() {
    const {token, getAllUser} = useAuth()
    const [allUser, setAllUser] = useState([])

    useEffect(() => {
        getAllUser(token).then(setAllUser)
            .catch(error => console.error(error))
    }, [getAllUser, token])

    const reloadBlogPage = () => {
        getAllUser(token)
            .then(setAllUser)
    }

    const eachUserListForBlog = allUser.map(fetchedUser => (
        <BlogGallery fetchedUserNameForBlog = {fetchedUser.userName}
                     key = {fetchedUser.id} reloadBlogPage={reloadBlogPage}
        />)
    )

    return (
        <div>
            <section>{eachUserListForBlog}</section>
        </div>

    )

}