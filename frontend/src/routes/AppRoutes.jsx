import { Routes, Route } from 'react-router-dom';

// Páginas de exemplo
const Home = () => <h1>Página Inicial</h1>;
const Agendamentos = () => <h1>Página de Agendamentos</h1>;
const Login = () => <h1>Página de Login</h1>;
const Cadastrar = () => <h1>Página de Cadastro</h1>;

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/"  element={<Home />} />
      <Route path="/agendamentos" element={<Agendamentos />} />
      <Route path="/login" element={<Login />} />
      <Route path="/cadastrar" element={<Cadastrar />} />
    </Routes>
  );
}