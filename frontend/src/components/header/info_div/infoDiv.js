import React from 'react';
import "components/header/info_div/infoDiv.scss";
import { useSelector } from 'react-redux';

function InfoDiv() {
    const infoMessage = useSelector(state => state.infoMessage);
    return (
        <div className={`info-div ${infoMessage.className}`}>
            <p>{infoMessage.message}</p>
        </div>
    );
}

export default InfoDiv;
