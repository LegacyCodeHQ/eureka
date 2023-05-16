import React from 'react';
import './App.css';
import VocabularyPanel from "./vocabulary/VocabularyPanel";
import EdgeBundlingGraph from "./viz/EdgeBundlingGraph";
import {graphData} from "./SampleData";

function App() {
  return (
    <div className="App">
      <div className="viz">
        <EdgeBundlingGraph data={JSON.parse(graphData)}/>
      </div>
      <VocabularyPanel/>
    </div>
  );
}

export default App;
