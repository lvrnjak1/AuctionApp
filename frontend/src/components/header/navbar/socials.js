import React from 'react'
import { makeStyles } from '@material-ui/styles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram, faTwitter, faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import { NavLink } from "react-router-dom"
import "components/header/navbar/navbar.scss";

const useStyles = makeStyles({
    icon: {
        color: "#9B9B9B",
        marginInline: 3,
        fontSize: 20
    }
})


function Socials() {
    const classes = useStyles();

    return (
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
    )
}

export default Socials;