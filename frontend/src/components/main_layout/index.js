import React from 'react'
import Navbar from '../navbar';
import Footer from '../footer';
import { Grid, makeStyles } from '@material-ui/core';

const useStyles = makeStyles({
    container: {
        width: "100%"
    }
})

function Layout(props) {
    const classes = useStyles();

    return (
        <Grid container
            direction="column" className={classes.container}>
            <Grid item><Navbar /></Grid>
            <Grid item>{props.children}</Grid>
            <Grid item><Footer /></Grid>
        </Grid>
    );
}

export default Layout;
