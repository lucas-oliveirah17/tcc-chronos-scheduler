import { NavLink, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './Navbar.css';

export function Navbar() {
  const navigate = useNavigate();
  const [estaLogado, setEstaLogado] = useState(!!localStorage.getItem("token"));

  useEffect(() => {
    // Atualiza o estado de login se o token mudar (ex: em outra aba)
    const atualizarStatus = () => setEstaLogado(!!localStorage.getItem("token"));
    
    // Ouvinte para um evento global de 'authChange' (disparado no login/logout)
    window.addEventListener("authChange", atualizarStatus);

    // Limpa o ouvinte quando o componente é desmontado
    return () => window.removeEventListener("authChange", atualizarStatus);
  }, []); // Array de dependências vazio, roda apenas ao montar

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.dispatchEvent(new Event("authChange")); // Dispara o evento global
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <NavLink to="/">
          <i className="fa fa-fw fa-home"></i> Home
        </NavLink>

        {/* Links de Gestão (Admin) - Só aparecem se estiver logado */}
        {estaLogado && (
          <div className="navbar-admin">

            {/* --- Dropdown: Agendamentos --- */}
            <div className="dropdown">
              <button className="dropdown-trigger">
                <i className="fa fa-fw fa-tasks"></i> Agendamentos
              </button>
              <div className="dropdown-content">
                <NavLink to="/admin/agendamentos">Listar/Gerenciar</NavLink>
              </div>
            </div>
            
            {/* --- Dropdown: Clientes --- */}
            <div className="dropdown">
              <button className="dropdown-trigger">
                <i className="fa fa-fw fa-users"></i> Clientes
              </button>
              <div className="dropdown-content">
                <NavLink to="/admin/clientes">Listar</NavLink>
              </div>
            </div>

            {/* --- Dropdown: Profissionais --- */}
            <div className="dropdown">
              <button className="dropdown-trigger">
                <i className="fa fa-fw fa-id-badge"></i> Profissionais
              </button>
              <div className="dropdown-content">
                <NavLink to="/admin/profissionais/novo">Cadastrar</NavLink>
                <NavLink to="/admin/profissionais">Listar</NavLink>
              </div>
            </div>

            {/* --- Dropdown: Serviços --- */}
            <div className="dropdown">
              <button className="dropdown-trigger">
                <i className="fa fa-fw fa-scissors"></i> Serviços
              </button>
              <div className="dropdown-content">
                <NavLink to="/admin/servicos/novo">Cadastrar</NavLink>
                <NavLink to="/admin/servicos">Listar</NavLink>
              </div>
            </div>

            {/* --- Dropdown: Usuários --- */}
            <div className="dropdown">
              <button className="dropdown-trigger">
                <i className="fa fa-fw fa-key"></i> Usuários
              </button>
              <div className="dropdown-content">
                <NavLink to="/admin/usuarios/novo">Cadastrar</NavLink>
                <NavLink to="/admin/usuarios">Listar</NavLink>
              </div>
            </div>

          </div>
        )}
      </div>

      {/* Seção de Autenticação (Direita) */}
      <div className="navbar-right">
        {!estaLogado ? (
          <>
            <NavLink to="/login">
              <i className="fa fa-fw fa-user"></i> Login
            </NavLink>
            <NavLink to="/cadastro">
              <i className="fa fa-fw fa-pencil-square-o"></i> Cadastrar
            </NavLink>
          </>
        ) : (
          <button id="deslogarbtn" onClick={handleLogout} >
            <i className="fa fa-fw fa-sign-out"></i> Deslogar
          </button>
        )}
      </div>
    </nav>
  );
}