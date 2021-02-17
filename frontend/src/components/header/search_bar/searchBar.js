import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { NavLink } from "react-router-dom"
import "./searchBar.css"


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
                        <NavLink to="/home" class="search-bar-link" activeStyle={{ color: "#8367D8", textDecoration: "none" }}>HOME</NavLink>
                    </li>
                    <li>
                        <NavLink to="/shop" class="search-bar-link" activeStyle={{ color: "#8367D8", textDecoration: "none" }}>SHOP</NavLink>
                    </li>
                    <li>
                        <NavLink to="/account" class="search-bar-link" activeStyle={{ color: "#8367D8", textDecoration: "none" }}>MY ACCOUNT</NavLink>
                    </li>
                </ul>
            </div>
        </div>
    )
}

export default SearchBar;