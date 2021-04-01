import React from 'react';
import "components/my_account/myAccount.scss";
import { Box, Tabs, Typography, Tab } from '@material-ui/core';
import { faUser, faThList, faGavel, faCog } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { makeStyles } from '@material-ui/styles';

const useStyles = makeStyles({
    labelContainer: {
        width: "auto",
        padding: 0
    },
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
                    <Typography>{children}</Typography>
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

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const tabs = [
        { label: "Profile", icon: faUser, index: 0, content: <div>one</div> },
        { label: "Seller", icon: faThList, index: 1, content: <div>two</div> },
        { label: "Bidds", icon: faGavel, index: 2, content: <div>three</div> },
        { label: "Settings", icon: faCog, index: 3, content: <div>four</div> },
    ]

    return (
        <div className="account-page">
            <Tabs className="tabs" indicatorColor="primary" value={value} onChange={handleChange}>
                {tabs.map(tab => {
                    return <Tab
                        className="tab"
                        label={tab.label}
                        icon={<FontAwesomeIcon className="tab-icon" icon={tab.icon} />}
                        classes={{
                            wrapper: classes.iconLabelWrapper,
                            labelContainer: classes.labelContainer
                        }}
                        {...a11yProps(tab.index)} />
                })}
            </Tabs>
            <div className="content">
                {tabs.map(tab => {
                    return <TabPanel value={value} index={tab.index}>
                        {tab.content}
                    </TabPanel>
                })}
            </div>
        </div>
    );
}

export default MyAccount;