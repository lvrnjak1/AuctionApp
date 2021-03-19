import React, { useState } from 'react';
import { Chip } from '@material-ui/core';
import "components/applied_filters/appliedFilters.scss";


function AppliedFilters() {
    const [chipData, setChipData] = useState([
        { key: 0, label: 'Filter 1' },
        { key: 1, label: 'Filter 2' },
        { key: 2, label: 'Filter 3' },
        { key: 3, label: 'Filter 4' },
        { key: 4, label: 'Filter 5' }, ,
        { key: 10, label: 'Filter 1' },
        { key: 11, label: 'Filter 2' },
        { key: 12, label: 'Filter 3' },
        { key: 13, label: 'Filter 4' },
        { key: 14, label: 'Filter 5' },
    ]);

    const handleDelete = (chipToDelete) => {
        setChipData((chips) => chips.filter((chip) => chip.key !== chipToDelete.key));
    };

    return (
        <ul className="filter-list">
            {chipData.map(data => {
                return (
                    <li key={data.key}>
                        <Chip
                            label={data.label}
                            onDelete={() => handleDelete(data)}
                            className="chip"
                        />
                    </li>
                )
            })}
        </ul>
    );
}

export default AppliedFilters;
