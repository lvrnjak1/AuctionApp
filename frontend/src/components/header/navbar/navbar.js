import React from 'react'
import { makeStyles } from '@material-ui/styles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram, faTwitter, faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import { Link, NavLink } from "react-router-dom"
import "./navbar.scss";

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
    return (
        <div id="navbar-black">
            <div id="navbar-socials">
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
            <div id="navbar-links">
                <ul>
                    <li><Link to="/login" className={classes.link}>Login</Link></li>
                    <li>or</li>
                    <li><Link to="/register" className={classes.link}>Create an Account</Link></li>
                </ul>
            </div>
        </div>
    )
}

export default Navbar;