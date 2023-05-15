import React, {useState} from 'react';
import TokenStatsTable from "./TokenStatsTable";
import {TokenStat} from "./model/TokenStat";
import {typeTokenStats} from "./model/SampleData";

const VocabularyPanel = () => {
  const [activeTab, setActiveTab] = useState('types');
  const [selectedTypeTokenStat, setSelectedTypeTokenStat] = useState<TokenStat | null>(null);

  const handleStatRowClick = (tokenStat: TokenStat | null) => {
    setSelectedTypeTokenStat(tokenStat);
  };

  return (
    <div>
      <div>
        <button onClick={() => setActiveTab('types')}>
          Types ({typeTokenStats.length})
        </button>
        <button onClick={() => setActiveTab('words')}>
          Words
        </button>
      </div>
      <div>
        {
          activeTab === 'types' && <TokenStatsTable tokenStats={typeTokenStats} onStatRowClick={handleStatRowClick}/>
        }
        {
          activeTab === 'words' && <div>Showing Words</div>
        }
      </div>
      {selectedTypeTokenStat &&
          <p className="debug-element">
              <span><b>Selected</b><br/>{selectedTypeTokenStat.token.name}: {selectedTypeTokenStat.frequency}</span>
          </p>
      }
    </div>
  );
}

export default VocabularyPanel;
