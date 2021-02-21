const loginUser = (user, token) => {
    localStorage.setItem("TOKEN", token);
    localStorage.setItem("USER", JSON.stringify(user));
}

const logoutUser = () => {
    localStorage.removeItem("TOKEN");
    localStorage.removeItem("USER");
}

const handleRememberMe = (rememberMe, email, password) => {
    //Or handle in a different way
    if (rememberMe) {
        localStorage.setItem("EMAIL", email);
        localStorage.setItem("PASSWORD", password);
    } else {
        localStorage.removeItem("EMAIL");
        localStorage.removeItem("PASSWORD");
    }
}

const getToken = () => {
    return localStorage.getItem("TOKEN");
}

const getUser = () => {
    return JSON.parse(localStorage.getItem("USER"));
}

const getEmail = () => {
    return localStorage.getItem("EMAIL") || "";
}

const getPassword = () => {
    return localStorage.getItem("PASSWORD") || "";
}

export {
    loginUser,
    logoutUser,
    handleRememberMe,
    getToken,
    getUser,
    getEmail,
    getPassword
}