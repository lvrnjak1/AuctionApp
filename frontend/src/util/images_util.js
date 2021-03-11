const getPublicId = (url) => {
    let splitUrl = url.split("upload/");
    const publicUrlWithExt = splitUrl[splitUrl.length - 1];
    return publicUrlWithExt.substr(0, publicUrlWithExt.lastIndexOf("."));
}

export {
    getPublicId
}