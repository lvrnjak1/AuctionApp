import React from 'react'
import { makeStyles } from '@material-ui/styles';
import { NavLink } from "react-router-dom"
import "components/footer/footer.scss";

const useStyles = makeStyles({
    link: {
        textDecoration: "none",
        color: "#FFFFFF"
    }
})

function Footer() {
    const classes = useStyles();
    return (
        <div className="footer">
            <div className="content-left">
                <p>AUCTION</p>
                <ul>
                    <li><NavLink to="/about" className={classes.link}>About Us</NavLink></li>
                    <li><NavLink to="/terms" className={classes.link}>Terms and Conditions</NavLink></li>
                    <li><NavLink to="/privacy" className={classes.link}>Privacy and Policy</NavLink></li>
                </ul>
            </div>
            <div className="content-right">
                <p>GET IN TOUCH</p>
                <ul>
                    <li >Call Us at +123 797-567-2535</li>
                    <li>support@auction.com</li>
                </ul>
            </div>
        </div>
    )
}

export default Footer;