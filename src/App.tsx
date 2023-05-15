import React, {useState} from 'react';
import './App.css';
import {Token} from "./vocabulary/model/Token";
import {TokenStat} from "./vocabulary/model/TokenStat";
import TokenStatsTable from "./vocabulary/TokenStatsTable";

let tokenStats = [
  new TokenStat(new Token("void"), 25),
  new TokenStat(new Token("boolean"), 9),
  new TokenStat(new Token("String"), 4),
  new TokenStat(new Token("int"), 4),
  new TokenStat(new Token("List"), 3),
  new TokenStat(new Token("View"), 3),
  new TokenStat(new Token("Status"), 2),
  new TokenStat(new Token("Bundle"), 2),
  new TokenStat(new Token("Activity"), 2),
];

function App() {
  const [selectedTokenStat, setSelectedTokenStat] = useState<TokenStat | null>(null);

  const handleStatRowClick = (tokenStat: TokenStat | null) => {
    setSelectedTokenStat(tokenStat);
  };

  return (
    <div className="App">
      <TokenStatsTable tokenStats={tokenStats} onStatRowClick={handleStatRowClick}/>
      {selectedTokenStat &&
          <p>
              <span><b>Selected</b> {selectedTokenStat.token.name}: {selectedTokenStat.frequency}</span>
          </p>
      }
    </div>
  );
}

export default App;
