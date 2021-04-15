import React, { useEffect, useState } from 'react';
import { dateToYMD, getTomorrow } from 'util/dateTimeService';
import { updateMessage } from 'util/info_div_util';

function StepTwo(props) {
    const [startDateTime, setStartDateTime] = useState(new Date());
    const [endDateTime, setEndDateTime] = useState(new Date());

    useEffect(() => {
        setStartDateTime(props.startDateTime);
        setEndDateTime(props.endDateTime);
    }, [props.startDateTime, props.endDateTime]);

    const handleOnSubmit = (e) => {
        e.preventDefault();
        if (new Date(startDateTime) - new Date(endDateTime) >= 0) {
            updateMessage("Invalid start and end date time.", "error");
        } else {
            props.handleNext(e);
        }
    }
    return (
        <form className="form" onSubmit={e => handleOnSubmit(e)}>
            <p className="form-title">SET PRICES</p>
            <div className="form-content step-two">
                <div className="input-label-group">
                    <label>Your start price</label>
                    <div className="input-wrapper">
                        <input className="input input-decor" disabled placeholder="$" />
                        <input
                            className="input"
                            required
                            type="number"
                            value={props.startPrice}
                            min={0}
                            max={1000000}
                            step={0.01}
                            onChange={e => props.setStartPrice(e.target.value)} />
                    </div>
                </div>
                <div className="flex-form-group">
                    <div className="input-label-group">
                        <label>Start date</label>
                        <input
                            className="input"
                            type="date"
                            required
                            value={startDateTime}
                            min={dateToYMD(getTomorrow())}
                            onChange={e => props.setStartDateTime(e.target.value)} />
                    </div>
                    <div className="input-label-group">
                        <label>End date</label>
                        <input
                            className="input"
                            type="date"
                            required
                            value={endDateTime}
                            min={dateToYMD(getTomorrow())}
                            onChange={e => props.setEndDateTime(e.target.value)} />
                    </div>
                </div>
                <p className="dates-info">The auction will be automatically closed when the end time comes. The highest bid will win the auction.</p>
            </div>
            <button type="submit" className="btn">
                {props.activeStep < props.maxSteps - 1 ? "Next" : "Submit"}
            </button>
            <button
                className="btn btn-back"
                onClick={props.handleBack}
                disabled={props.activeStep === 0}>
                Back
            </button>
        </form>
    );
}

export default StepTwo;


