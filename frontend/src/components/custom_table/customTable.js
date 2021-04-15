import React from 'react';
import "components/custom_table/customTable.scss";
import { TablePagination } from '@material-ui/core';

function CustomTable(props) {
    return (
        props.data.length > 0 ? <div>
            <table className="table">
                <thead>
                    <tr className="headers">
                        {props.headings.map((th, index) => {
                            return <th key={index} className="header">{th}</th>
                        })}
                    </tr>
                </thead>
                <tbody>
                    {props.data.map((row, index) => {
                        return <tr key={index} className="data-row">
                            {row.map((td, dIndex) => {
                                return <td key={dIndex}
                                    className={`data-cell ${dIndex === 1 || dIndex === 6 ? "large-cell" : ""}`}>
                                    {td}
                                </td>
                            })}
                        </tr>
                    })}
                </tbody>
            </table>
            <TablePagination
                component="div"
                count={props.pagination.available}
                page={props.pagination.pageNumber - 1}
                onChangePage={props.onChangePage}
                rowsPerPage={props.rowsPerPage}
                onChangeRowsPerPage={props.onChangeRowsPerPage} />
        </div> : <p className="no-items">Nothing to show now!</p>
    );
}

export default CustomTable;
