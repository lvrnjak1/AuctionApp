import React from 'react';
import { TableContainer, Table, TableBody, TableRow, TableCell, Paper, TableHead } from '@material-ui/core';
import "components/table/table.scss";

const formatDate = (date) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString(undefined, options);
}

function CustomTable(props) {
    const { items } = props;
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
                    {items.length > 0 ? items.map((row) => (
                        <TableRow key={row.bidder.name}>
                            <TableCell className="col col-bidder" component="th" scope="row">
                                <div className="avatar">
                                    <img src="/images/profile-pic.png" alt="profile" />
                                </div>
                                <p> {`${row.bidder.name} ${row.bidder.surname}`}</p>
                            </TableCell>
                            <TableCell className="col col-date" align="right">
                                {formatDate(new Date(row.dateTime))}
                            </TableCell>
                            <TableCell className="col col-bid" align="right">
                                {`$ ${row.amount.toFixed(2)}`}
                            </TableCell>
                        </TableRow>
                    )) : <TableRow>
                            <TableCell>There are no bids for this product. Be the first one!</TableCell>
                        </TableRow>}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default CustomTable;
