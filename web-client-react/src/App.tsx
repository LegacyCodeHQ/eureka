import React from 'react';
import './App.css';
import VocabularyPanel from "./vocabulary/VocabularyPanel";
import EdgeBundlingGraph from "./viz/EdgeBundlingGraph";
import GraphDataSource from "./source/GraphDataSource";
import {GraphData} from "./viz/model/GraphData";

function App() {
  return (
    <GraphDataSource>
      {(data: GraphData) =>
        <div className="App">
          <div className="viz">
            <EdgeBundlingGraph data={data}/>
          </div>
          <VocabularyPanel data={data}/>
        </div>
      }
    </GraphDataSource>
  );
}

export default App;
