import { useState, useEffect } from 'react';

function App() {
  const [isConnected, setIsConnected] = useState(true);

  useEffect(() => {
    const handleConnectionChange = () => {
      if (navigator.onLine) {
        setIsConnected(true);
      } else {
        setIsConnected(false);
      }
    };
    window.addEventListener('online', handleConnectionChange);
    window.addEventListener('offline', handleConnectionChange);
    return () => {
      window.removeEventListener('online', handleConnectionChange);
      window.removeEventListener('offline', handleConnectionChange);
    };
  }, []);

  return (
    <div>
      {!isConnected && (
        <p>You have lost your connection to the server. Please check your internet connection and try again.</p>
      )}
      {/* render the main content of the application */}
    </div>
  );
}

export default App;
