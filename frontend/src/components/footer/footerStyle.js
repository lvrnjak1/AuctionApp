const style = {
    root: {
        width: "100%",
        backgroundColor: "#252525",
        color: "#FFFFFF",
        fontSize: "16px",
        letterSpacing: "0.56px",
        textAlign: "center",
        fontFamily: "Lato",
        marginTop: "20px"
    },
    content: {
        paddingLeft: "20",
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
    },
    contentDiv: {
        opacity: 0.5,
        textAlign: "left"
    },
    contentList: {
        listStyle: "none",
        textAlign: "left",
        padding: 0,
    },
    contentListItem: {
        marginBottom: 5
    },
    footer: {
        height: 300
    },
    headerIcon: {
        fontSize: 20,
        marginInline: 3,
        color: "#9B9B9B"
    },
    headerItemLink: {
        color: "#FFFFFF",
        textDecoration: "none",
        '&:hover': {
            textDecoration: 'none'
        }
    },
};

export default style;