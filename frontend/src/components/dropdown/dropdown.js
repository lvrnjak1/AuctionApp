import React from 'react';
import "components/dropdown/dropdown.scss";
import { NavLink } from 'react-router-dom';

function Dropdown(props) {
    const content = [
        { to: "/account/profile", text: "Profile", index: 0 },
        { to: "/account/seller", text: "Become Seller", index: 1 },
        { to: "/account/bids", text: "Your bids", index: 2 },
        { to: "/account/settings", text: "Settings", index: 3 }
    ]
    return (
        <div className="dropdown" onMouseLeave={props.onLeave}>
            {content.map(link => {
                return <NavLink
                    key={link.text}
                    to={{
                        pathname: link.to,
                        state: { index: link.index }
                    }}
                    className="dropdown-link"
                    activeStyle={{ color: "#8367D8", textDecoration: "none" }}>
                    {link.text}
                </NavLink>
            })}
        </div>
    );
}

export default Dropdown;