import {useAuth} from "../auth/AuthProvider";
import Button from "../components/Button";
import {deleteVideo} from "../service/apiService";


export default function VideoCard({videoId, reloadPage}) {
    const {user, token} = useAuth()


    const source = "https://www.youtube.com/embed/" + videoId


    const handleDelete = () => {
        deleteVideo(videoId, token)
            .then(reloadPage)
            .catch(error => console.error(error))
    }

    return (
        <section>
            <iframe width="350" height="210" src={source}
                    title="YouTube video player" frameBorder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                    allowFullScreen></iframe>
            <div>
                {user.role === "admin" && <Button onClick={handleDelete}>Löschen</Button>}
            </div>
        </section>

    )
}