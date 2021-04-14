import React from 'react';
import "components/forms/forms.scss";
import "components/profile/profile.scss";
import { getMonths, getYears } from 'util/dateTimeService';

function ExpirationDatePicker(props) {
    const years = getYears();
    const months = getMonths();
    return (
        <div className="pickers">
            <label>Expiration date</label>
            <select
                required={props.required}
                className="input small-select"
                value={props.month}
                onChange={e => props.handleMonthSelect(e.target.value)}>
                <option value="">Month</option>
                {months.map(month => {
                    return <option value={month.key} key={month.key}>{month.name}</option>
                })}
            </select>
            <select
                required={props.required}
                className="input small-select"
                value={props.year}
                onChange={e => props.handleYearSelect(e.target.value)}>
                <option value="">Year</option>
                {years.map(year => {
                    return <option value={year} key={year}>{year}</option>
                })}
            </select>
        </div>

    );
}

export default ExpirationDatePicker;
