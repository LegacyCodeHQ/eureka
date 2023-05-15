import React, {useState} from 'react';
import TokenStatsTable from "./TokenStatsTable";
import {TokenStat} from "./model/TokenStat";
import {Token} from "./model/Token";

let typeTokenStats = [
  new TokenStat(new Token("void"), 25),
  new TokenStat(new Token("Attachment"), 10),
  new TokenStat(new Token("boolean"), 9),
  new TokenStat(new Token("String"), 4),
  new TokenStat(new Token("Poll"), 4),
  new TokenStat(new Token("int"), 4),
  new TokenStat(new Token("List"), 3),
  new TokenStat(new Token("View"), 3),
  new TokenStat(new Token("Status"), 2),
  new TokenStat(new Token("Bundle"), 2),
  new TokenStat(new Token("Activity"), 2),
  new TokenStat(new Token("Shuffle"), 2),
  new TokenStat(new Token("FragmentManager"), 2),
  new TokenStat(new Token("TransactionManager"), 2),
  new TokenStat(new Token("ArrayList"), 2),
  new TokenStat(new Token("Toolbar"), 1),
  new TokenStat(new Token("TextView"), 1),
  new TokenStat(new Token("ProgressBar"), 1),
  new TokenStat(new Token("SelfUpdateStateChangedEvent"), 1),
  new TokenStat(new Token("UpdateState"), 1),
  new TokenStat(new Token("Holder"), 1),
  new TokenStat(new Token("StatusCreatedEvent"), 1),
  new TokenStat(new Token("Configuration"), 1),
  new TokenStat(new Token("MenuItem"), 1),
  new TokenStat(new Token("Menu"), 1),
  new TokenStat(new Token("MenuInflater"), 1),
  new TokenStat(new Token("APIRequest"), 1),
  new TokenStat(new Token("AnimatorSet"), 1),
  new TokenStat(new Token("Button"), 1),
  new TokenStat(new Token("ImageView"), 1),
  new TokenStat(new Token("CheckBox"), 1),
  new TokenStat(new Token("ImageButton"), 1),
];

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
