import React from 'react';
import { TableContainer, Table, TableBody, TableRow, TableCell, Paper, TableHead } from '@material-ui/core';
import "components/table/table.scss";

const formatDate = (date) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString(undefined, options);
}

function CustomTable(props) {
    return (
        <TableContainer className="table-container" component={Paper}>
            <Table >
                <TableHead>
                    <TableRow className="header">
                        <TableCell className="header-item" component="th" scope="row">Bidder</TableCell>
                        <TableCell className="header-item" align="right">Date</TableCell>
                        <TableCell className="header-item" align="right">Bid</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.items ? props.items.map((row) => (
                        <TableRow key={row.name}>
                            <TableCell className="col col-bidder" component="th" scope="row">
                                <div className="avatar"></div>
                                <p> {row.name}</p>
                            </TableCell>
                            <TableCell className="col col-date" align="right">
                                {formatDate(new Date(row.date))}
                            </TableCell>
                            <TableCell className="col col-bid" align="right">
                                {`$ ${row.bid.toFixed(2)}`}
                            </TableCell>
                        </TableRow>
                    )) : "There are no bids for this product yet. Be the first one!"}
                </TableBody>
            </Table>
        </TableContainer>
    );
}

export default CustomTable;
