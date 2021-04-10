import React, { useEffect, useMemo } from 'react';
import "components/my_account/myAccount.scss";
import { faUser, faThList, faGavel, faCog } from "@fortawesome/free-solid-svg-icons";
import { useHistory, useLocation } from 'react-router-dom';
import Profile from 'components/profile/profile';
import Seller from 'components/seller/seller';
import Settings from 'components/settings/settings';
import Bids from 'components/bids/bids';
import CustomTabs from 'components/tabs/tabs';
import { getToken } from 'util/auth/auth';

// const useStyles = makeStyles({
//     iconLabelWrapper: {
//         flexDirection: "row"
//     }
// });

// function TabPanel(props) {
//     const { children, value, index, ...other } = props;

//     return (
//         <div
//             role="tabpanel"
//             hidden={value !== index}
//             id={`simple-tabpanel-${index}`}
//             aria-labelledby={`simple-tab-${index}`}
//             {...other}
//         >
//             {value === index && (
//                 <Box p={3}>
//                     <Typography component="span">{children}</Typography>
//                 </Box>
//             )}
//         </div>
//     );
// }

// function a11yProps(index) {
//     return {
//         id: `simple-tab-${index}`,
//         'aria-controls': `simple-tabpanel-${index}`,
//     };
// }

function MyAccount() {
    const [value, setValue] = React.useState(0);
    // const classes = useStyles();
    const location = useLocation();
    const history = useHistory();

    useEffect(() => {
        if (!getToken()) {
            history.push("/404");
        }
    }, []);

    const tabs = useMemo(() => {
        return [
            { label: "Profile", path: "/profile", icon: faUser, index: 0, content: <Profile /> },
            { label: "Seller", path: "/seller", icon: faThList, index: 1, content: <Seller /> },
            { label: "Bids", path: "/bids", icon: faGavel, index: 2, content: <Bids /> },
            { label: "Settings", path: "/settings", icon: faCog, index: 3, content: <Settings /> },
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