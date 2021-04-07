import React from 'react';
import "components/custom_table/customTable.scss";

function CustomTable(props) {

    return (
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
                            return <td key={dIndex} className="data-cell">{td}</td>
                        })}
                    </tr>
                })}
            </tbody>
        </table>
    );
}

export default CustomTable;
