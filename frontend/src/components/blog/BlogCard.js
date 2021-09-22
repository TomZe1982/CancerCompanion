import { useHistory } from "react-router-dom";
import Button from "../Button";
import BlogImage from "./BlogImage";
import Section from "../styled/Section";


export default function BlogCard({fetchedUserNameForBlog}) {
    const history = useHistory()

    function handleClickReadBlog() {
        history.push(`/userblogs/${fetchedUserNameForBlog}`)
    }

    return (<div>
            <Section>
                <BlogImage className="user__image" src="https://thispersondoesnotexist.com/image" alt="userImage"/>
                <h4 className="user__name">{fetchedUserNameForBlog}</h4>
                <Button className="button" onClick={handleClickReadBlog} >Blog lesen</Button>
            </Section>

        </div>
    )

}

//as={Link} to="/userblogs"