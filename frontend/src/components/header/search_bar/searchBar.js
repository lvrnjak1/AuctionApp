import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { NavLink } from "react-router-dom"
import "components/header/search_bar/searchBar.scss"


function SearchBar() {
    return (
        <div className="search-bar">
            <div className="search-bar-title">
                <FontAwesomeIcon icon={faGavel} size="sm" className="search-bar-title-icon" />
                <p>AUCTION</p>
            </div>
            <div className="search-bar-links">
                <ul>
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