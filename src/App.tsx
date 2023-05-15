import React from 'react';
import './App.css';
import {Token} from "./vocabulary/model/Token";
import {TokenStat} from "./vocabulary/model/TokenStat";
import TokenStatRow from "./vocabulary/TokenStatRow";

function App() {
  let tokenStat = new TokenStat(new Token("TextView"), 5);

  return (
    <div className="App">
      <TokenStatRow serialNumber={1} tokenStat={tokenStat}/>
    </div>
  );
}

export default App;
