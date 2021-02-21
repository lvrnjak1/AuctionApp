import React from 'react'
import { makeStyles } from '@material-ui/styles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram, faTwitter, faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import { faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import { Link, NavLink, useHistory } from "react-router-dom"
import "components/header/navbar/navbar.scss";
import { useSelector } from "react-redux";
import { getUser, logoutUser } from "util/auth/auth";
import { useDispatch } from 'react-redux';
import { resetLoggedIn } from 'state/actions/loggedInActions';

const useStyles = makeStyles({
    link: {
        textDecoration: "none",
        color: "#FFFFFF",
        fontWeight: "bold",
        fontSize: 14,
        letterSpacing: 0.49
    },
    icon: {
        color: "#9B9B9B",
        marginInline: 3,
        fontSize: 20
    }
})


function Navbar() {
    const classes = useStyles();
    const user = getUser();
    const dispatch = useDispatch();
    const history = useHistory();
    const loggedIn = useSelector(state => state.loggedIn)

    const handleLogout = () => {
        dispatch(resetLoggedIn());
        logoutUser();
        history.push("/home");
    }

    const navBarLinks = <div className="navbar-links">
        <ul>
            <li><Link to="/login" className={classes.link}>Login</Link></li>
            <li>or</li>
            <li><Link to="/register" className={classes.link}>Create an Account</Link></li>
        </ul>
    </div>

    const logout = user ? <div className="navbar-links">
        <ul>
            <li>{`Hello, ${user.name} ${user.surname}`}</li>
            <li>|</li>
            <li>
                <button className="logout-button" onClick={handleLogout}>
                    <FontAwesomeIcon icon={faSignOutAlt} className={classes.icon} />
                </button>
            </li>
        </ul>
    </div> : "";

    return (
        <div className="navbar-black">
            <div className="navbar-socials">
                <NavLink to="/home">
                    <FontAwesomeIcon icon={faFacebook} className={classes.icon} />
                </NavLink>
                <NavLink to="/home">
                    <FontAwesomeIcon icon={faInstagram} className={classes.icon} />
                </NavLink>
                <NavLink to="/home">
                    <FontAwesomeIcon icon={faTwitter} className={classes.icon} />
                </NavLink>
                <NavLink to="/home">
                    <FontAwesomeIcon icon={faGooglePlus} className={classes.icon} />
                </NavLink>
            </div>
            {loggedIn ? logout : navBarLinks}
        </div>
    )
}

export default Navbar;