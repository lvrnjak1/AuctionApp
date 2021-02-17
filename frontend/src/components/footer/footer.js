import React from 'react'
import { makeStyles } from '@material-ui/styles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram, faTwitter, faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import { NavLink } from "react-router-dom"
import "components/footer/footer.scss";

const useStyles = makeStyles({
    link: {
        textDecoration: "none",
        color: "#FFFFFF"
    },
    icon: {
        color: "#9B9B9B",
        marginInline: 3,
        fontSize: 20
    }
})

function Footer() {
    const classes = useStyles();
    return (
        <div className="footer">
            <div className="content-left">
                <p>AUCTION</p>
                <ul>
                    <li><NavLink to="/shop/about" className={classes.link}>About Us</NavLink></li>
                    <li><NavLink to="/shop/terms" className={classes.link}>Terms and Conditions</NavLink></li>
                    <li><NavLink to="/shop/privacy" className={classes.link}>Privacy and Policy</NavLink></li>
                </ul>
            </div>
            <div class="content-right">
                <p>GET IN TOUCH</p>
                <ul>
                    <li >Call Us at +123 797-567-2535</li>
                    <li>support@auction.com</li>
                </ul>
                <div >
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
            </div>
        </div>
    )
}

export default Footer;