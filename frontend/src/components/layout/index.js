import React, { useEffect, useState } from 'react'
import Navbar from '../header/navbar';
import Footer from '../footer';
import { Grid, makeStyles } from '@material-ui/core';
import SearchBar from '../header/search-bar';

const useStyles = makeStyles({
    container: {
        width: "100%"
    },
    navbar: {
        height: 40
    }
})

function Layout(props) {
    const classes = useStyles();

    return (
        <Grid container
            direction="column" className={classes.container}>
            {!props.removeHeader ?
                <>
                    <Grid item className={classes.navbar}><Navbar /></Grid>
                    <Grid item ><SearchBar></SearchBar></Grid>
                </> : ""}
            <Grid item>{props.children}</Grid>
            <Grid item><Footer /></Grid>
        </Grid>
    );
}

export default Layout;
