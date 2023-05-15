import React from 'react';
import './App.css';
import {Token} from "./vocabulary/model/Token";
import {TokenStat} from "./vocabulary/model/TokenStat";
import TokenStatRow from "./vocabulary/TokenStatRow";

let tokenStat = new TokenStat(new Token("TextView"), 5);

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
      <table>
        <thead>
        <tr>
          <th>#</th>
          <th>Type</th>
          <th>Freq.</th>
        </tr>
        </thead>
        <tbody>
        {tokenStats.map((tokenStat, index) =>
          <TokenStatRow serialNumber={index + 1} tokenStat={tokenStat}/>
        )}
        </tbody>
      </table>
      <TokenStatRow serialNumber={1} tokenStat={tokenStat}/>
    </div>
  );
}

export default App;
