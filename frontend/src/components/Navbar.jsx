import { NavLink, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './Navbar.css'; // Importa o CSS que acabamos de criar

export function Navbar() {
  const navigate = useNavigate();
  const [estaLogado, setEstaLogado] = useState(!!localStorage.getItem("token"));

  useEffect(() => {
    const atualizarStatus = () => setEstaLogado(!!localStorage.getItem("token"));

    // Ouvinte do evento customizado
    window.addEventListener("authChange", atualizarStatus);

    return () => window.removeEventListener("authChange", atualizarStatus);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.dispatchEvent(new Event("authChange")); // dispara o evento global
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <NavLink to="/">
          <i className="fa fa-fw fa-home"></i> Home
        </NavLink>

        <NavLink to="/agendamentos">
          <i className="fa fa-fw fa-calendar"></i> Agendar
        </NavLink>
      </div>

      {/* Autenticação - Orientado à direita da Navbar*/}
      <div className="navbar-right">
        {!estaLogado ?(
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