import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons"
import "static_pages/not_found/notFound.scss"
import { Link } from 'react-router-dom';


function NotFound() {
    return (
        <div className="not-found-content">
            <div className="not-found-title">
                <FontAwesomeIcon icon={faGavel} size="lg" />
                <p className="not-found-title-text">AUCTION</p>
            </div>
            <p className="not-found-code">404</p>
            <p className="not-found-text">Ooops! Looks like the page is Not Found!</p>
            <Link to="/home">
                <button>GO BACK</button>
            </Link>
        </div>
    );
}

export default NotFound;