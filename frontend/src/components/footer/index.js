import React from 'react'
import { Grid, Link, Container } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram, faTwitter, faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import style from './style';

const useStyles = makeStyles(style)

function Footer() {
    const classes = useStyles();
    return (
        <Container className={classes.root} maxWidth="xl">
            <Grid container justify="center" className={classes.footer} xs={12}>
                <Grid item xs={3} />
                <Grid item className={classes.content} xs={3}>
                    <div>
                        <div className={classes.contentDiv}>AUCTION</div>
                        <ul className={classes.contentList}>
                            <li className={classes.contentListItem}>
                                <Link href="#" className={classes.headerItemLink}>About Us</Link>
                            </li>
                            <li className={classes.contentListItem}>
                                <Link href="#" className={classes.headerItemLink}>Terms and Conditions</Link>
                            </li>
                            <li className={classes.contentListItem}>
                                <Link href="#" className={classes.headerItemLink}>Privacy and Policy</Link>
                            </li>
                        </ul>
                    </div>
                </Grid>
                <Grid item className={classes.content} xs={3} >
                    <div>
                        <div className={classes.contentDiv}>GET IN TOUCH</div>
                        <ul className={classes.contentList}>
                            <li className={classes.contentListItem}>Call Us at +123 797-567-2535</li>
                            <li className={classes.contentListItem}>support@auction.com</li>
                        </ul>
                        <div className={classes.contentDiv}>
                            <Link href="#" className={classes.headerItemLink}>
                                <FontAwesomeIcon icon={faFacebook} className={classes.headerIcon} />
                            </Link>
                            <Link href="#" className={classes.headerItemLink}>
                                <FontAwesomeIcon icon={faInstagram} className={classes.headerIcon} />
                            </Link>
                            <Link href="#" className={classes.headerItemLink}>
                                <FontAwesomeIcon icon={faTwitter} className={classes.headerIcon} />
                            </Link>
                            <Link href="#" className={classes.headerItemLink}>
                                <FontAwesomeIcon icon={faGooglePlus} className={classes.headerIcon} />
                            </Link>
                        </div>
                    </div>
                </Grid>
                <Grid item xs={3} />
            </Grid>
        </Container>
    )
}

export default Footer;