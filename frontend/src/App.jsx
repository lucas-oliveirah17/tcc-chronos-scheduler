import { AppRoutes } from './routes/AppRoutes'

function App() {
  return(
    <div>
      <header>
        <h1>Chronos: Sistema de Agendamento</h1>
        {/*Links de Navegação*/}
        <nav>
          <a href="/">Home</a> | <a href="/login">Login</a>
        </nav>
      </header>
      <hr />

      <main>
        {/*Onde as páginas (Home, Login) são renderizadas*/}
        <AppRoutes />
      </main>
    </div>
  )
}

export default App