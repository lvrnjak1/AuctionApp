import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom"
import "./index.css"


function SearchBar() {
    return (
        <div id="search-bar">
            <div id="search-bar-title">
                <FontAwesomeIcon icon={faGavel} size="sm" id="search-bar-title-icon" />
                <p id="search-bar-title-text">AUCTION</p>
            </div>
            <div id="search-bar-links">
                <ul id="search-bar-links-list">
                    <li>
                        <Link to="/home" class="search-bar-link">HOME</Link>
                    </li>
                    <li>
                        <Link to="/home" class="search-bar-link">SHOP</Link>
                    </li>
                    <li>
                        <Link to="/home" class="search-bar-link">MY ACCOUNT</Link>
                    </li>
                </ul>
            </div>
        </div>
    )
}

export default SearchBar;