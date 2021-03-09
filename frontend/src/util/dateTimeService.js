const getDifferenceBetweenDates = (date1, date2) => {
    let unit = "seconds";
    let dif = (date2 - date1) / 1000;
    if (dif < 0) {
        return null;
    }

    if (dif > 60) {
        unit = "minutes";
        dif = dif / 60;

        if (dif > 60) {
            unit = "hours";
            dif = dif / 60;
        }
        if (dif > 24) {
            unit = "days";
            dif = dif / 24;
        }
    }
    return { dif, unit };
}

const formatDate = (date) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString(undefined, options);
}

export {
    getDifferenceBetweenDates,
    formatDate
}