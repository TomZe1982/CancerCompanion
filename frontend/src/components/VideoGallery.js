import VideoCard from "./VideoCard";


export default function VideoGallery( { videoId } ){

    return(
        <div>
                <section className="video-gallery">
                    <VideoCard videoId = {videoId} />
            </section>
        </div>
    )

}