import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { NavLink } from "react-router-dom"
import "components/header/search_bar/searchBar.scss"
import Loader from 'react-loader-spinner';
import { useSelector } from 'react-redux';
import { makeStyles } from '@material-ui/core';

const useStyles = makeStyles(theme => ({
    loader: {
        display: "inline"
    }
}))

function SearchBar() {
    const asyncInProgress = useSelector(state => state.asyncInProgress);
    const classes = useStyles();
    return (
        <div className="search-bar">
            <div className="search-bar-title">
                {asyncInProgress ?
                    <Loader className={classes.loader} type="TailSpin" color="#8367d8" height="20" width="20" timeout={5000} />
                    :
                    <FontAwesomeIcon icon={faGavel} size="sm" className="search-bar-title-icon" />}
                <p>AUCTION</p>
            </div>
            <div className="search-bar-links">
                <ul>
                    <li>
                        <NavLink to="/home" className="search-bar-link" activeStyle={{ color: "#8367D8", textDecoration: "none" }}>HOME</NavLink>
                    </li>
                    <li>
                        <NavLink to="/shop" className="search-bar-link" activeStyle={{ color: "#8367D8", textDecoration: "none" }}>SHOP</NavLink>
                    </li>
                </ul>
            </div>
        </div>
    )
}

export default SearchBar;