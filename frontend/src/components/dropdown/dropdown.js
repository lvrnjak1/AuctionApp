import React from 'react';
import "components/dropdown/dropdown.scss";
import { NavLink } from 'react-router-dom';

function Dropdown() {
    const content = [
        { to: "/account/profile", text: "Profile" },
        { to: "/account/seller", text: "Become Seller" },
        { to: "/account/bids", text: "Your bids" },
        { to: "/account/settings", text: "Settings" }
    ]
    return (
        <div className="dropdown">
            {content.map(link => {
                return <NavLink
                    key={link.text}
                    to={link.to}
                    className="dropdown-link"
                    activeStyle={{ color: "#8367D8", textDecoration: "none" }}>
                    {link.text}
                </NavLink>
            })}
        </div>
    );
}

export default Dropdown;