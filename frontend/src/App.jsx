import { AppRoutes } from './routes/AppRoutes';
import { Navbar } from './components/Navbar';
import './App.css';

function App() {
  return(
    <div className="app-container">
      <Navbar />

      <main className="main-content">
        <AppRoutes />
      </main>
    </div>
  )
}

export default App