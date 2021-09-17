import EachUser from "./EachUser";


export default function userGallery( { fetchedUserName, reloadUserPage }) {

    return (
        <div>
        <section className="user-gallery">
            <EachUser fetchedUserName={fetchedUserName} reloadUserPage = {reloadUserPage}
            />
        </section>
    </div>
)
}