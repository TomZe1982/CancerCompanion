import axios from 'axios'

const headers = token => ({
    headers: {
        Authorization: `Bearer ${token}`,
    },
})

//Route without token needed

export const getToken = credentials =>
    axios
        .post('/auth/access_token', credentials)
        .then(response => response.data)
        .then(dto => dto.token)

export const createUser = credentials =>
    axios.post("/api/tomze/register", credentials)
        .then(response => response.data)

export const getInfos = () =>
    axios.get("/api/tomze/info/all")
        .then(response => response.data)

export const getInfoById = (infoId) =>
    axios.get("/api/tomze/info/all/"+infoId)
        .then(response => response.data)


//Routes with token needed

export const getAllUser = (token) =>
    axios.get("/api/tomze/user", headers(token))
        .then(response => response.data)

export const getUser =(userName, token) =>
    axios.get("api/tomze/user/"+userName, headers(token))
        .then(response => response.data)

export const updateUser = (credentials, token) =>
    axios.put("api/tomze/user/update/"+credentials.userName, credentials, headers(token))

export const getVideoList = (token) =>
    axios.get("api/tomze/videolist", headers(token))
        .then(response => response.data)

export const getNewVideo = (videoId, token) =>
    axios.get("api/tomze/videos/"+videoId, headers(token))
        .then(response => response.data)

export const deleteVideo = (videoId, token) =>
    axios.delete("api/tomze/videos/"+videoId, headers(token))

export const deleteUser = (userName, token) =>
    axios.delete("api/tomze/user/delete/"+userName, headers(token) )

export const resetPassword = (userName, token) =>
    axios.put("/api/tomze/user/resetpassword/"+userName, userName, headers(token) )
        .then(response => response.data)

export const getBlogList = (userName, token) =>
    axios.get("/api/tomze/blog/allblogs/"+userName, headers(token))
        .then(response => response.data)

export const getBlogEntry = (userName,blogId, token) =>
    axios.get("/api/tomze/blog/allblogs/" + userName + "/" + blogId, headers(token))
        .then(response => response.data)

export const postBlogEntry = (blogEntry, token) =>
    axios.post("/api/tomze/blog/newblog", blogEntry, headers(token))
        .then(response => response.data)

export const deleteBlogEntry = (userName, blogId, token) =>
    axios.delete("/api/tomze/blog/delete/" +userName + "/" + blogId, headers(token))

export const postInfo = (credentials, token) =>
    axios.post("/api/tomze/info" , credentials, headers(token))

export const deleteInfo = (infoId, token) =>
    axios.delete("/api/tomze/info/"+ infoId, headers(token))

export const updateInfo = (infoId, credentials, token) =>
    axios.put("/api/tomze/info/"+infoId, credentials, headers(token))



