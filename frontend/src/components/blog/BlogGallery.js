import BlogCard from "./BlogCard";
import Section from "../styled/Section";
import Box from "../styled/Box";


export default function BlogGallery({ fetchedUserNameForBlog, reloadBlogPage}) {

    console.log(fetchedUserNameForBlog)

    return (
        <div>
            <Section className="blog-gallery">
                <Box>
                <BlogCard fetchedUserNameForBlog={fetchedUserNameForBlog} reloadBlogPage={reloadBlogPage} />
                </Box>
            </Section>
        </div>
    )

}
