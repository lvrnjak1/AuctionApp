const loginUser = (user, token, rememberMe) => {
    const storage = rememberMe ? localStorage : sessionStorage;
    storage.setItem("TOKEN", token);
    storage.setItem("USER", JSON.stringify(user));
}

const logoutUser = () => {
    sessionStorage.removeItem("TOKEN");
    sessionStorage.removeItem("USER");
    localStorage.removeItem("TOKEN");
    localStorage.removeItem("USER");
}

const getToken = () => {
    return localStorage.getItem("TOKEN") || sessionStorage.getItem("TOKEN");
}

const getUser = () => {
    return JSON.parse(localStorage.getItem("USER")) || JSON.parse(sessionStorage.getItem("USER"));
}

const getAuthorizationConfig = () => {
    return { headers: { Authorization: `Bearer ${getToken()}` } };
}

export {
    loginUser,
    logoutUser,
    getToken,
    getUser,
    getAuthorizationConfig
}