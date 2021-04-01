const getFormattedParams = (filterParams) => {
    const ids = (filterParams.categoryId && filterParams.categoryId.length > 0) ?
        filterParams.categoryId.map(id => `${id}`).join(',') :
        null;
    return { ...filterParams, categoryId: ids };
}

export {
    getFormattedParams
}