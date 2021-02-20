const loginUser = (user, token) => {
    localStorage.setItem("TOKEN", token);
    localStorage.setItem("USER", JSON.stringify(user));
}

const logoutUser = () => {
    localStorage.clear();
}

const getToken = () => {
    return localStorage.getItem("TOKEN");
}

const getUser = () => {
    return JSON.parse(localStorage.getItem("USER"));
}

export {
    loginUser,
    logoutUser,
    getToken,
    getUser
}