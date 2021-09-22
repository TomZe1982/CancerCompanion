import {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthProvider";
import BlogGallery from "./BlogGallery";
import {getAllUser} from "../../service/apiService";



export default function EachUserMapperBlog() {
    const {token} = useAuth()
    const [allUser, setAllUser] = useState([])

    useEffect(() => {
        getAllUser(token).then(setAllUser)
            .catch(error => console.error(error))
    }, [token])

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
            <section>
                {eachUserListForBlog}
            </section>
        </div>

    )

}