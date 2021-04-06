import React, { useState } from 'react';
import ImageUploader from "components/seller/image_uploader/imageUploader";


function StepTwo(props) {
    const [startPrice, setStartPrice] = useState(0);
    const [startDateTime, setStartDateTime] = useState(new Date());
    const [endDateTime, setEndDateTime] = useState(new Date());

    return (
        <>
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
                            value={startPrice}
                            min={0}
                            max={1000000}
                            onChange={e => setStartPrice(e.target.value)} />
                    </div>
                </div>
                <div className="flex-form-group">
                    <div className="input-label-group">
                        <label>Start date</label>
                        <input
                            className="input"
                            type="date"
                            value={startDateTime}
                            onChange={e => setStartDateTime(e.target.value)} />
                    </div>
                    <div className="input-label-group">
                        <label>End date</label>
                        <input
                            className="input"
                            type="date"
                            value={endDateTime}
                            onChange={e => setEndDateTime(e.target.value)} />
                    </div>
                </div>
                <p className="dates-info">The auction will be automatically closed when the end time comes. The highest bid will win the auction.</p>
            </div>
        </>
    );
}

export default StepTwo;


