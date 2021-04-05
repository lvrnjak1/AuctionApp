import React, { useEffect } from 'react';
import "components/my_account/myAccount.scss";
import { Box, Tabs, Typography, Tab } from '@material-ui/core';
import { faUser, faThList, faGavel, faCog } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { makeStyles } from '@material-ui/styles';
import { useHistory, useLocation } from 'react-router-dom';
import Profile from 'components/profile/profile';

const useStyles = makeStyles({
    iconLabelWrapper: {
        flexDirection: "row"
    }
});

function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box p={3}>
                    <Typography component="span">{children}</Typography>
                </Box>
            )}
        </div>
    );
}

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

function MyAccount() {
    const [value, setValue] = React.useState(0);
    const classes = useStyles();
    const location = useLocation();
    const history = useHistory();

    const tabs = [
        { label: "Profile", path: "/account/profile", icon: faUser, index: 0, content: <Profile /> },
        { label: "Seller", path: "/account/seller", icon: faThList, index: 1, content: "" },
        { label: "Bidds", path: "/account/bids", icon: faGavel, index: 2, content: "" },
        { label: "Settings", path: "/account/settings", icon: faCog, index: 3, content: "" },
    ]

    useEffect(() => {
        const currentTab = tabs.find(tab => tab.path === location.pathname);
        setValue(currentTab.index);
    }, [location.pathname])


    const handleChange = (event, newValue) => {
        setValue(newValue);
        history.replace(tabs[newValue].path);
    };

    return (
        <div className="account-page">
            <Tabs className="tabs" indicatorColor="primary" value={value} onChange={handleChange}>
                {tabs.map(tab => {
                    return <Tab
                        key={tab.index}
                        className="tab"
                        label={tab.label}
                        icon={<FontAwesomeIcon className="tab-icon" icon={tab.icon} />}
                        classes={{
                            wrapper: classes.iconLabelWrapper
                        }}
                        {...a11yProps(tab.index)} />
                })}
            </Tabs>
            <div className="content">
                {tabs.map(tab => {
                    return <TabPanel key={tab.index} value={value} index={tab.index}>
                        {tab.content}
                    </TabPanel>
                })}
            </div>
        </div>
    );
}

export default MyAccount;