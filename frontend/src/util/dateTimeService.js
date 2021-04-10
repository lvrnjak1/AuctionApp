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

const getTimeLeft = (endDateTime) => {
    const now = new Date();
    const end = new Date(endDateTime);
    const { dif, unit } = getDifferenceBetweenDates(now, end);
    return `${dif.toFixed(0)} ${unit}`;
}

const formatDate = (date) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString(undefined, options);
}

const dateToYMD = (date) => {
    var d = date.getDate();
    var m = date.getMonth() + 1;
    var y = date.getFullYear();
    return '' + y + '-' + (m <= 9 ? '0' + m : m) + '-' + (d <= 9 ? '0' + d : d);
}

const getMaxBirthdate = () => {
    let date = new Date();
    date.setFullYear(date.getFullYear() - 18);
    return dateToYMD(date);
}

const getTomorrow = () => {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    return tomorrow;
}

export {
    getDifferenceBetweenDates,
    getTimeLeft,
    formatDate,
    dateToYMD,
    getMaxBirthdate,
    getTomorrow
}