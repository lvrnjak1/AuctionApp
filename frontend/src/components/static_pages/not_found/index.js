import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons"
import "./index.css"


function NotFound() {
    return (
        <div id="not-found-content">
            <div id="not-found-title">
                <FontAwesomeIcon icon={faGavel} size="lg" id="not-found-title-icon" />
                <p id="not-found-title-text">AUCTION</p>
            </div>
            <p id="not-found">404</p>
            <p id="not-found-text">Ooops! Looks like the page is Not Found!</p>
            <button id="not-found-back-button">GO BACK</button>
        </div>
    );
}

export default NotFound;