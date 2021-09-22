import { useHistory } from "react-router-dom";
import Button from "../Button";
import BlogImage from "./BlogImage";
import Section from "../Section";
import {useAuth} from "../../auth/AuthProvider";


export default function BlogCard({fetchedUserNameForBlog}) {
    const { user } = useAuth()
    const history = useHistory()

    function handleClickReadBlog() {
        history.push(`/userblogs/${fetchedUserNameForBlog}`)
    }

    function handleClickUpdateBlog() {
            return null
    }

    return (<div>
            <Section>
                <BlogImage className="user__image" src="https://thispersondoesnotexist.com/image" alt="userImage"/>
                <h4 className="user__name">{fetchedUserNameForBlog}</h4>
                <Button className="button" onClick={handleClickReadBlog} >Blog lesen</Button>
                {user.userName === fetchedUserNameForBlog && <Button className="button" onClick={handleClickUpdateBlog} >Update Blog</Button>}
            </Section>

        </div>
    )

}

//as={Link} to="/userblogs"