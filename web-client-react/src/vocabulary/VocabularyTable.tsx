import { TokenStat } from './model/TokenStat';
import React, { useState } from 'react';
import './VocabularyTable.css';
import VocabularyTableBody from './VocabularyTableBody';

interface TokenStatsTableProps {
  kind: string;
  tokenStats: TokenStat[];
  onStatRowClick: (tokenStat: TokenStat | null) => void;
}

const VocabularyTable: React.FC<TokenStatsTableProps> = ({ kind, tokenStats, onStatRowClick }) => {
  const [selectedTokenStat, setSelectedTokenStat] = useState<TokenStat | null>(null);

  const handleStatRowClick = (tokenStat: TokenStat) => {
    const deselect = tokenStat.isEqual(selectedTokenStat);

    if (deselect) {
      setSelectedTokenStat(null);
      onStatRowClick(null);
    } else {
      setSelectedTokenStat(tokenStat);
      onStatRowClick(tokenStat);
    }
  };

  return (
    <div className="table-container">
      <table>
        <thead>
          <tr>
            <th>
              <div className="row-content">
                <span className="column-serial">#</span>
                <span className="column-name">{kind}</span>
                <span className="column-count number">Count</span>
              </div>
            </th>
          </tr>
        </thead>
      </table>
      <div className="table-body-container">
        <VocabularyTableBody
          tokenStats={tokenStats}
          selectedTokenStat={selectedTokenStat}
          onStatRowClick={handleStatRowClick}
        />
      </div>
    </div>
  );
};

export default VocabularyTable;
