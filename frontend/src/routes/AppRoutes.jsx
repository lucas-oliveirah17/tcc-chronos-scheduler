import { Routes, Route } from 'react-router-dom';
import { Home } from '../pages/Home'; 
import { Login } from '../pages/Login'; 
import { Cadastro } from '../pages/Cadastro'; 

import { AgendamentosPage } from '../pages/admin/AgendamentosPage';
import { AgendamentoCreatePage } from '../pages/admin/AgendamentoCreatePage';
import { AgendamentoEditPage } from '../pages/admin/AgendamentoEditPage';

import { ServicosPage } from '../pages/admin/ServicosPage';
import { ServicoCreatePage } from '../pages/admin/ServicoCreatePage';
import { ServicoEditPage } from '../pages/admin/ServicoEditPage';

import { ProfissionaisPage } from '../pages/admin/ProfissionalPage';
import { ProfissionalCreatePage } from '../pages/admin/ProfissionalCreatePage';
import { ProfissionalEditPage } from '../pages/admin/ProfissionalEditPage';

import { UsuariosPage } from '../pages/admin/UsuariosPage';
import { UsuarioCreatePage } from '../pages/admin/UsuarioCreatePage';
import { UsuarioEditPage } from '../pages/admin/UsuarioEditPage';

import { ClientesPage } from '../pages/admin/ClientesPage';
import { ClienteEditPage } from '../pages/admin/ClienteEditPage';

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/cadastro" element={<Cadastro />} />

      <Route path="/admin/agendamentos" element={<AgendamentosPage />} />
      <Route path="/admin/agendamentos/novo" element={<AgendamentoCreatePage />} />
      <Route path="/admin/agendamentos/editar/:id" element={<AgendamentoEditPage />} />
      
      <Route path="/admin/servicos" element={<ServicosPage />} />
      <Route path="/admin/servicos/novo" element={<ServicoCreatePage />} />
      <Route path="/admin/servicos/editar/:id" element={<ServicoEditPage />} />

      <Route path="/admin/profissionais" element={<ProfissionaisPage />} />
      <Route path="/admin/profissionais/novo" element={<ProfissionalCreatePage />} />
      <Route path="/admin/profissionais/editar/:id" element={<ProfissionalEditPage />} />

      <Route path="/admin/usuarios" element={<UsuariosPage />} />
      <Route path="/admin/usuarios/novo" element={<UsuarioCreatePage />} />
      <Route path="/admin/usuarios/editar/:id" element={<UsuarioEditPage />} />

      <Route path="/admin/clientes" element={<ClientesPage />} />
      <Route path="/admin/clientes/editar/:id" element={<ClienteEditPage />} />


    </Routes>
  );
}