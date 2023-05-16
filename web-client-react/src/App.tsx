import React from 'react';
import './App.css';
import VocabularyPanel from './vocabulary/VocabularyPanel';
import EdgeBundlingGraph from './viz/EdgeBundlingGraph';
import GraphDataSource from './source/GraphDataSource';
import { GraphData } from './viz/model/GraphData';

function App() {
  return (
    <GraphDataSource>
      {(data: GraphData | null) =>
        <div className="App">
          <div className="viz">
            {data &&
                <EdgeBundlingGraph data={data}/>
            }
          </div>
          {data &&
              <VocabularyPanel data={data}/>
          }
        </div>
      }
    </GraphDataSource>
  );
}

export default App;
