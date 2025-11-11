import { Routes, Route } from 'react-router-dom';
import { Agendamentos } from '../pages/Agendamentos';
import { Cadastro } from '../pages/Cadastro';
import { Home } from '../pages/Home'
import { Login } from '../pages/Login'

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/"  element={<Home />} />
      <Route path="/agendamentos" element={<Agendamentos />} />
      <Route path="/login" element={<Login />} />
      <Route path="/cadastro" element={<Cadastro />} />
    </Routes>
  );
}