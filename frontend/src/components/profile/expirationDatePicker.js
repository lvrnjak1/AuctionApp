import React from 'react';
import "components/forms/forms.scss";
import "components/profile/profile.scss";

const months = [
    { name: "Jan", key: 0 },
    { name: "Feb", key: 1 },
    { name: "Mar", key: 2 },
    { name: "April", key: 3 },
    { name: "May", key: 4 },
    { name: "June", key: 5 },
    { name: "July", key: 6 },
    { name: "Aug", key: 7 },
    { name: "Sept", key: 8 },
    { name: "Oct", key: 9 },
    { name: "Nov", key: 10 },
    { name: "Dec", key: 11 }
]

function getYears() {
    let years = [];
    const currentYear = new Date().getFullYear();
    for (let i = currentYear; i <= currentYear + 10; i++) {
        years.push(i);
    }

    return years;
}

const years = getYears();

function ExpirationDatePicker(props) {
    return (
        <div className="pickers">
            <label>Expiration date</label>
            <select className="input small-select" defaultValue={undefined} onChange={e => props.handleMonthSelect(e.target.value)}>
                <option value={undefined}>Month</option>
                {months.map(month => {
                    return <option value={month.key} key={month.key}>{month.name}</option>
                })}
            </select>
            <select className="input small-select" defaultValue={undefined} onChange={e => props.handleYearSelect(e.target.value)}>
                <option value={undefined}>Year</option>
                {years.map(year => {
                    return <option value={year} key={year}>{year}</option>
                })}
            </select>
        </div>

    );
}

export default ExpirationDatePicker;
