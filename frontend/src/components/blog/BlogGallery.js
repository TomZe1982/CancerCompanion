import BlogCard from "./BlogCard";
import Section from "../Section";
import Box from "../Box";


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
