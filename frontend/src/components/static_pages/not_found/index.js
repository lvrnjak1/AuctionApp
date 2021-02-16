import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons"
import "./index.css"


function NotFound() {
    return (
        <div id="content">
            <div id="title">
                <FontAwesomeIcon icon={faGavel} size="lg" id="title-icon" />
                <p id="title-text">AUCTION</p>
            </div>
            <p id="not-found">404</p>
            <p id="text">Ooops! Looks like the page is Not Found!</p>
            <button id="back-button">GO BACK</button>
        </div>
    );
}

export default NotFound;