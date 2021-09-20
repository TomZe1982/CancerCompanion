import EachUser from "./EachUser";


export default function userGallery( {key,  fetchedUserName, reloadUserPage }) {

    return (
        <div>
        <section className="user-gallery">
            <EachUser fetchedUserName={fetchedUserName} reloadUserPage = {reloadUserPage} key = {key} />
        </section>
    </div>
)
}