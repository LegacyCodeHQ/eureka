import React, { useEffect, useState } from 'react';
import './AppVersion.css';

const AppVersion: React.FC = () => {
  if (process.env.NODE_ENV === 'development') {
    return <AppVersionDev />;
  }
  return <AppVersionProd />;
};

const AppVersionProd: React.FC = () => {
  const [versionName, setVersionName] = useState<string | null>(null);

  useEffect(() => {
    fetch('http://localhost:7070/version')
      .then((response) => response.text())
      .then((data) => {
        setVersionName(data);
      });
  }, []);

  return <span className="app-version">v{versionName}</span>;
};

const AppVersionDev: React.FC = () => {
  return <span className="app-version">v2023.1.0-DEMO</span>;
};

export default AppVersion;
