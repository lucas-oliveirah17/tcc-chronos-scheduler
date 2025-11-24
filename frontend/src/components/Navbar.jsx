import { NavLink, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Menu, X, Home, Calendar, ShoppingBag, UserCog, Briefcase, Scissors, LogOut, Users, IdCardLanyard } from 'lucide-react';
import './Navbar.css';

export function Navbar() {
  const navigate = useNavigate();
  const [estaLogado, setEstaLogado] = useState(!!localStorage.getItem("token"));
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const atualizarStatus = () => setEstaLogado(!!localStorage.getItem("token"));
    window.addEventListener("authChange", atualizarStatus);
    return () => window.removeEventListener("authChange", atualizarStatus);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("usuario");
    window.dispatchEvent(new Event("authChange"));
    navigate("/login");
    setMenuOpen(false);
  };

  const toggleMenu = () => setMenuOpen(!menuOpen);

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-brand" onClick={() => navigate('/')}>
          <span className="brand-text">Chronos</span>
        </div>

        {/* Mobile Menu Toggle */}
        <div className="mobile-toggle" onClick={toggleMenu}>
          {menuOpen ? <X size={24} /> : <Menu size={24} />}
        </div>

        {/* Desktop & Mobile Menu Links */}
        <div className={`navbar-links ${menuOpen ? 'active' : ''}`}>
          <NavLink to="/" className="nav-item" onClick={() => setMenuOpen(false)}>
            <Home size={18} /> Home
          </NavLink>

          {estaLogado && (
            <>
              <div className="nav-group">
                <span className="nav-group-title">Gestão</span>

                <NavLink to="/admin/agendamentos" className="nav-item" onClick={() => setMenuOpen(false)}>
                  <Calendar size={18} /> Agendamentos
                </NavLink>

                <NavLink to="/admin/clientes" className="nav-item" onClick={() => setMenuOpen(false)}>
                  <Users size={18} /> Clientes
                </NavLink>

                <NavLink to="/admin/profissionais" className="nav-item" onClick={() => setMenuOpen(false)}>
                  <IdCardLanyard size={18} /> Profissionais
                </NavLink>

                <NavLink to="/admin/servicos" className="nav-item" onClick={() => setMenuOpen(false)}>
                  <Scissors size={18} /> Serviços
                </NavLink>

                <NavLink to="/admin/usuarios" className="nav-item" onClick={() => setMenuOpen(false)}>
                  <UserCog size={18} /> Usuários
                </NavLink>
              </div>
            </>
          )}

          <div className="nav-auth">
            {!estaLogado ? (
              <button className="btn btn-primary" onClick={() => { navigate('/login'); setMenuOpen(false); }}>
                Login
              </button>
            ) : (
              <button className="btn btn-secondary logout-btn" onClick={handleLogout}>
                <LogOut size={18} /> Sair
              </button>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}