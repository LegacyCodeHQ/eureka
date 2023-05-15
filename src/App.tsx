import React from 'react';
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
  return (
    <div className="App">
      <TokenStatsTable tokenStats={tokenStats}/>
    </div>
  );
}

export default App;
