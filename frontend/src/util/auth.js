const loginUser = (token) => {
    localStorage.setItem("TOKEN", token);
}

const logoutUser = () => {
    localStorage.removeItem("TOKEN");
}

const getToken = () => {
    return localStorage.getItem("TOKEN");
}

export {
    loginUser,
    logoutUser,
    getToken
}