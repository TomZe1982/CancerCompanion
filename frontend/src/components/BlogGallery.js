
import BlogCard from "./BlogCard";


export default function BlogGallery( {key, fetchedUserNameForBlog , reloadBlogPage} ) {

    console.log(fetchedUserNameForBlog)

    return (
        <div>
            <section className="blog-gallery">
            <BlogCard fetchedUserNameForBlog = {fetchedUserNameForBlog} reloadBlogPage = {reloadBlogPage} key = {key}  />
            </section>
        </div>
    )

}
