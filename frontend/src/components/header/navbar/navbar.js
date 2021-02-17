import React from 'react'
import { AppBar, Toolbar, Typography } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram, faTwitter, faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import clsx from 'clsx';
import style from './navbarStyle';
import { Link } from "react-router-dom"
const useStyles = makeStyles(style)

function Navbar() {
    const classes = useStyles();
    return (
        <AppBar color="secondary" position="static">
            <Toolbar className={classes.header}>
                <div className={classes.headerSocials}>
                    <Link to="/home" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faFacebook} className={classes.headerIcon} />
                    </Link>
                    <Link to="/home" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faInstagram} className={classes.headerIcon} />
                    </Link>
                    <Link to="/home" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faTwitter} className={classes.headerIcon} />
                    </Link>
                    <Link to="/home" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faGooglePlus} className={classes.headerIcon} />
                    </Link>
                </div>
                <Typography>
                    <Link to="/login" className={clsx(classes.headerItemLink, classes.root)}>
                        Login
                </Link>
                    <Typography display="inline" className={clsx(classes.headerItem, classes.root)}>{' '}or {' '}</Typography>
                    <Link to="/register" className={clsx(classes.headerItemLink, classes.root)}>
                        Create an Account
                </Link>
                </Typography>
            </Toolbar>
        </AppBar >
    )
}

export default Navbar;