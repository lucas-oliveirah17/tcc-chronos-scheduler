import { NavLink } from 'react-router-dom';
import './Navbar.css'; // Importa o CSS que acabamos de criar

export function Navbar() {
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
        <NavLink to="/login" className="navbar-right">
          <i className="fa fa-fw fa-user"></i> Login
        </NavLink>

        <NavLink to="/cadastrar" className="navbar-right">
          <i className="fa fa-fw fa-pencil-square-o"></i> Cadastrar
        </NavLink>
      </div>
    </nav>
  );
}