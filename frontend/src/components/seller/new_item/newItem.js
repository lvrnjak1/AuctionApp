import React, { useState } from 'react';
import { Step, StepLabel, Stepper } from '@material-ui/core';
import StepOne from './step1';
import "components/seller/new_item/newItem.scss";
import StepTwo from './step2';

function getSteps() {
    return [<StepOne />, <StepTwo />]
}

function NewItem(props) {
    const [activeStep, setActiveStep] = useState(0);
    const steps = getSteps();

    const handleNext = (e) => {
        e.preventDefault();
        if (activeStep === steps.length - 1) {

        } else {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
        }
    };

    const handleBack = (e) => {
        e.preventDefault();
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    return (
        <div>
            <Stepper activeStep={activeStep} >
                {steps.map((step, index) => {
                    return <Step key={index}><StepLabel></StepLabel></Step>
                })}
            </Stepper>
            <form className="form">
                {steps[activeStep]}
                <button type="submit" className="btn" onClick={handleNext}>
                    {activeStep < steps.length - 1 ? "Next" : "Submit"}
                </button>
                <button type="submit" className="btn btn-back" onClick={handleBack} disabled={activeStep === 0}>Back</button>
            </form>
        </div>
    );
}

export default NewItem;


