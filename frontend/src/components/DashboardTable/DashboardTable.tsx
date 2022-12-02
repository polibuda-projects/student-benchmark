import ContainerBox from '@components/ContainerBox/ContainerBox';
import { Key } from 'react';
import React, { FC } from 'react';

import style from './DashboardTable.module.css';

export type TableContent = {
  testName?: string;
  personalBest?: string;
  averageScore?: string;
  percentile?: string;
  indexValue?: string;
  nickname?: string;
  date?: string;
  score?: string;
}[];

export type ColumnTitles = string[];

const DashboardTable: FC<{tableContent: TableContent, columnTitles: ColumnTitles}> = ({ tableContent, columnTitles }) => {
  return (
    <ContainerBox width={'100rem'}>
      {
        <table cellSpacing="0" className={style.dashboardTable}>
          <thead className={style.dashboardTableHead}>
            <tr className={style.dashboardTableHeadRow}>
              {columnTitles.map((columnTitle: string, index: Key) => (
                <th key={index} className={style.dashboardTableTitle}>{columnTitle}</th>
              ))}
            </tr>
          </thead>
          <tbody className={style.dashboardTableBody}>
            {Object.values(tableContent).map((object, index) => (
              <tr key={index} className={index%2===1?`${style.dashboardTableBodyRow} ${style.shadow}`:`${style.dashboardTableBodyRow}`}>
                {Object.values(object).map((value, secondIndex) => (
                  <td key={secondIndex} className={style.dashboardTableCell}>{value}</td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      }
    </ContainerBox>
  );
};

export default DashboardTable;

