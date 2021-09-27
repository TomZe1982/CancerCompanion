import {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthProvider";
import {getAllUser} from "../../service/apiService";
import BlogCard from "./BlogCard";
import TextField from "../TextField";




export default function EachUserMapperBlog() {
    const {token} = useAuth()
    const [allUser, setAllUser] = useState([])
    const [foundUser, setFoundUser] = useState("")

    const handleChange = (event) => {
        setFoundUser(event.target.value)
    }

    useEffect(() => {
        getAllUser(token).then(setAllUser)
            .catch(error => console.error(error))
    }, [token])

    const reloadBlogPage = () => {
        getAllUser(token)
            .then(setAllUser)
    }

    const filteredUser = allUser.filter(fetchedUser => (
        fetchedUser.userName.toLowerCase().includes(foundUser.toLowerCase())))



    const eachUserListForBlog = filteredUser.map(fetchedUser => (
        <BlogCard fetchedUserNameForBlog = {fetchedUser.userName}
                     key = {fetchedUser.id} reloadBlogPage={reloadBlogPage}
        />)
    )

    return (        <div>
        <TextField
            name="userName"
            value={foundUser}
            onChange={handleChange}
        />
            <section>
                {eachUserListForBlog}
            </section>
        </div>
    )

}

