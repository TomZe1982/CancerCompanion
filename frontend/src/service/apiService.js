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



//Routes with token needed

export const getAllUser = (token) =>
    axios.get("/api/tomze/user", headers(token))
        .then(response => response.data)

export const getUser =(userName, token) =>
    axios.get("api/tomze/user/"+userName, headers(token))
        .then(response => response.data)

export const deleteUser = (userName, token) =>
    axios.delete("api/tomze/user/delete/"+userName, headers(token) )

export const updateUser = (credentials, token) =>
    axios.put("api/tomze/user/update/"+credentials.userName, credentials, headers(token))

export const getVideoList = (token) =>
    axios.get("api/tomze/videolist", headers(token))
        .then(response => response.data)

export const getNewVideo = (videoId, token) =>
    axios.get("api/tomze/videos/"+videoId, headers(token))




