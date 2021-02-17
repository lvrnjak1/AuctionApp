import React from 'react'
import Navbar from '../header/navbar/navbar';
import Footer from '../footer/footer';
import { Grid, makeStyles } from '@material-ui/core';
import SearchBar from '../header/search_bar/searchBar';
import BreadcrumbBar from '../header/breadcrumb_bar/breadcrumbBar';

const useStyles = makeStyles({
    container: {
        width: "100%"
    },
    navbar: {
        height: 40
    },
    searchbar: {
        height: 60
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
                    <Grid item className={classes.searchbar}><SearchBar /></Grid>
                    <Grid item><BreadcrumbBar content={props.breadcrumbs} /></Grid>
                </> : ""}
            <Grid item>{props.children}</Grid>
            <Grid item><Footer /></Grid>
        </Grid>
    );
}

export default Layout;
