import React, { useEffect } from 'react'
import Navbar from 'components/header/navbar/navbar';
import Footer from 'components/footer/footer';
import { Grid, makeStyles } from '@material-ui/core';
import SearchBar from 'components/header/search_bar/searchBar';
import BreadcrumbBar from 'components/header/breadcrumb_bar/breadcrumbBar';
import InfoDiv from 'components/header/info_div/infoDiv';
import { useDispatch } from 'react-redux';
import { resetInfoMessage } from 'state/actions/infoMessageActions';
import { useLocation } from 'react-router-dom';

const useStyles = makeStyles(theme => ({
    container: {
        width: "100%"
    },
    searchbar: {
        height: 100,
        [theme.breakpoints.up('md')]: {
            height: 60,
        }
    }
}))

function Layout(props) {
    const classes = useStyles();
    const dispatch = useDispatch();
    const { pathname } = useLocation();

    useEffect(() => {
        dispatch(resetInfoMessage());
    });

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname])

    return (
        <Grid container direction="column" className={classes.container}>
            {!props.removeHeader &&
                <>
                    <Grid item><Navbar /></Grid>
                    <Grid item className={classes.searchbar}><SearchBar /></Grid>
                    <Grid item><BreadcrumbBar content={props.breadcrumbs} /></Grid>
                    <Grid item><InfoDiv /></Grid>
                </>
            }
            <Grid item>{props.children}</Grid>
            <Grid item><Footer /></Grid>
        </Grid>
    );
}

export default Layout;
