import React, { useEffect, useMemo } from 'react';
import "components/my_account/myAccount.scss";
import { faUser, faThList, faGavel, faCog, faGift } from "@fortawesome/free-solid-svg-icons";
import { useHistory, useLocation } from 'react-router-dom';
import Profile from 'components/profile/profile';
import Seller from 'components/seller/seller';
import Settings from 'components/settings/settings';
import Bids from 'components/bids/bids';
import CustomTabs from 'components/tabs/tabs';
import { getToken } from 'util/auth/auth';
import Wishlist from 'components/wishlist/wishlist';

function MyAccount() {
    const [value, setValue] = React.useState(0);
    const location = useLocation();
    const history = useHistory();

    useEffect(() => {
        if (!getToken()) {
            history.push("/404");
        }
    }, [history]);

    const tabs = useMemo(() => {
        return [
            { label: "Profile", path: "/profile", icon: faUser, index: 0, content: <Profile /> },
            { label: "Seller", path: "/seller", icon: faThList, index: 1, content: <Seller /> },
            { label: "Bids", path: "/bids", icon: faGavel, index: 2, content: <Bids /> },
            { label: "Wishlist", path: "/wishlist", icon: faGift, index: 3, content: <Wishlist /> },
            { label: "Settings", path: "/settings", icon: faCog, index: 4, content: <Settings /> },
        ]
    }, []);

    useEffect(() => {
        const currentTab = tabs.find(tab => tab.path === location.pathname);
        setValue(currentTab.index);
    }, [location.pathname, tabs])


    const handleChange = (event, newValue) => {
        setValue(newValue);
        history.replace(tabs[newValue].path);
    };

    return (
        <div className="account-page">
            <CustomTabs tabs={tabs} value={value} handleChange={handleChange} withIcon={true} />
        </div>
    );
}

export default MyAccount;