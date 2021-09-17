


export default function VideoCard( { videoId }){

    const source = "https://www.youtube.com/embed/"+videoId

    return(
        <section>
            <iframe width="350" height="210" src={source}
                    title="YouTube video player" frameBorder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                    allowFullScreen></iframe>
        </section>
    )
}