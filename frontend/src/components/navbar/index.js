import React from 'react'
import { AppBar, Toolbar, Typography, Link } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram, faTwitter, faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import clsx from 'clsx';
import style from './style';

const useStyles = makeStyles(style)

function Navbar() {
    const classes = useStyles();
    return (
        <AppBar color="secondary" position="static">
            <Toolbar className={classes.header}>
                <div className={classes.headerSocials}>
                    <Link href="#" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faFacebook} className={classes.headerIcon} />
                    </Link>
                    <Link href="#" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faInstagram} className={classes.headerIcon} />
                    </Link>
                    <Link href="#" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faTwitter} className={classes.headerIcon} />
                    </Link>
                    <Link href="#" className={clsx(classes.headerItemLink, classes.root)}>
                        <FontAwesomeIcon icon={faGooglePlus} className={classes.headerIcon} />
                    </Link>
                </div>
                <Typography>
                    <Link href="#" className={clsx(classes.headerItemLink, classes.root)}>
                        Login
                    </Link>
                    <Typography display="inline" className={clsx(classes.headerItem, classes.root)}>{' '}or {' '}</Typography>
                    <Link href="#" className={clsx(classes.headerItemLink, classes.root)}>
                        Create an Account
                    </Link>
                </Typography>
            </Toolbar>
        </AppBar >
    )
}

export default Navbar;