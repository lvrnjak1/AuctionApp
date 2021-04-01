import React, { useEffect, useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { NavLink, useHistory, useLocation } from "react-router-dom"
import "components/header/search_bar/searchBar.scss"
import Loader from 'react-loader-spinner';
import { useDispatch, useSelector } from 'react-redux';
import { makeStyles } from '@material-ui/core';
import { faSearch } from '@fortawesome/free-solid-svg-icons'
import { setName } from 'state/actions/filterParamsActions';
import { resetSearch, setSearch } from 'state/actions/searchActions';
import Dropdown from 'components/dropdown/dropdown';

const useStyles = makeStyles(theme => ({
    loader: {
        display: "inline"
    }
}))

function SearchBar() {
    const asyncInProgress = useSelector(state => state.asyncInProgress);
    const classes = useStyles();
    const searchCriteria = useSelector(state => state.searchCriteria);
    const history = useHistory();
    const dispatch = useDispatch();
    const { pathname } = useLocation();
    const [openDropdown, setOpenDropdown] = useState(false);

    const handleSearch = (e) => {
        e.preventDefault();
        dispatch(setName(searchCriteria));
        history.push("/shop");
    }

    useEffect(() => {
        if (history.location.pathname !== "/shop") {
            dispatch(resetSearch(""));
        }
    }, [history.location.pathname, dispatch]);

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
                        <NavLink
                            to="/home"
                            isActive={() => ['/home', '/'].includes(pathname)}
                            className="search-bar-link"
                            activeStyle={{ color: "#8367D8", textDecoration: "none" }}>
                            HOME
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            to="/shop"
                            className="search-bar-link"
                            activeStyle={{ color: "#8367D8", textDecoration: "none" }}>
                            SHOP
                        </NavLink>
                    </li>
                    <li>
                        <div
                            className="dropdown-container"
                            onMouseOver={() => setOpenDropdown(true)}
                            onMouseLeave={() => setOpenDropdown(false)}>
                            <NavLink
                                to="/account"
                                className="search-bar-link"
                                activeStyle={{ color: "#8367D8", textDecoration: "none" }}>
                                MY ACCOUNT
                        </NavLink>
                            {openDropdown && <Dropdown />}
                        </div>
                    </li>
                </ul>
            </div>

            <div className="search-input">
                <form onSubmit={handleSearch}>
                    <input type="text" placeholder="Search..." value={searchCriteria} onChange={(e) => dispatch(setSearch(e.target.value))} />
                    <button type="submit" className="search-button">
                        <FontAwesomeIcon icon={faSearch} size="sm" />
                    </button>
                </form>
            </div>
        </div>
    )
}

export default SearchBar;