import React, { useEffect, useState } from 'react';
import { Box, Tabs, Typography, Tab } from '@material-ui/core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { makeStyles } from '@material-ui/styles';

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

function CustomTabs(props) {
    const [value, setValue] = useState(0);
    const classes = useStyles();

    useEffect(() => {
        setValue(props.value);
    }, [props.value])

    return (
        <div>
            <Tabs className="tabs" indicatorColor="primary" value={value} onChange={props.handleChange}>
                {props.tabs.map(tab => {
                    return <Tab
                        key={tab.index}
                        className="tab"
                        label={tab.label}
                        icon={props.withIcon ? <FontAwesomeIcon className="tab-icon" icon={tab.icon} /> : ""}
                        classes={{
                            wrapper: classes.iconLabelWrapper
                        }}
                        {...a11yProps(tab.index)} />
                })}
            </Tabs>
            <div className="content">
                {props.tabs.map(tab => {
                    return <TabPanel key={tab.index} value={value} index={tab.index}>
                        {tab.content}
                    </TabPanel>
                })}
            </div>
        </div>
    );
}

export default CustomTabs;
