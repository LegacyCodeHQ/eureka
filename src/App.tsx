import React from 'react';
import './App.css';
import VocabularyPanel from "./vocabulary/VocabularyPanel";
import EdgeBundlingGraph from "./viz/EdgeBundlingGraph";
import {graphData} from "./SampleData";
import {parseGraphData} from "./viz/Model";

function App() {
  let data = parseGraphData(graphData);

  return (
    <div className="App">
      <div className="viz">
        <EdgeBundlingGraph data={data}/>
      </div>
      <VocabularyPanel/>
    </div>
  );
}

export default App;
