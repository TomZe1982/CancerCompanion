import BlogCard from "./BlogCard";
import Box from "../styled/Box";


export default function BlogGallery({fetchedUserNameForBlog, reloadBlogPage}) {

    return (
        <section>
            <Box>
                <BlogCard fetchedUserNameForBlog={fetchedUserNameForBlog} reloadBlogPage={reloadBlogPage}/>
            </Box>
        </section>
    )

}
