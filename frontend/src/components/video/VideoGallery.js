import VideoCard from "./VideoCard";


export default function VideoGallery( { videoId, reloadPage } ){

    return(
        <div>
                <section className="video-gallery">
                    <VideoCard videoId = {videoId} reloadPage = {reloadPage} />
            </section>
        </div>
    )

}