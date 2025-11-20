import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from '../services/api';
import { User, Lock, LogIn } from 'lucide-react';

export function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ email: '', senha: '' });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await api.post('/auth/login', formData);
    
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("usuario", JSON.stringify(response.data.usuario));

      window.dispatchEvent(new Event("authChange"));
      navigate("/");
    } catch (error) {
      console.error("Erro ao logar:", error);
      alert("Falha no login. Verifique suas credenciais.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container" style={{ minHeight: '80vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <div className="card" style={{ maxWidth: '400px', width: '100%', backdropFilter: 'blur(10px)', backgroundColor: 'rgba(30, 41, 59, 0.7)' }}>
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <h2 style={{ fontSize: '2rem', marginBottom: '0.5rem', background: 'linear-gradient(135deg, #00C6FF 0%, #0072FF 100%)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>Chronos</h2>
          <p style={{ color: 'var(--text-muted)' }}>Bem-vindo de volta</p>
        </div>

        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              name="email"
              id="email"
              placeholder="seu@email.com"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <label htmlFor="senha">Senha</label>
            <input
              type="password"
              name="senha"
              id="senha"
              placeholder="••••••••"
              value={formData.senha}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className="btn btn-primary" style={{ width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '0.5rem' }} disabled={loading}>
            {loading ? 'Entrando...' : <>Entrar <LogIn size={18} /></>}
          </button>
        </form>

        <div style={{ marginTop: '1.5rem', textAlign: 'center', fontSize: '0.9rem', color: 'var(--text-muted)' }}>
          Não tem uma conta?{' '}
          <span
            onClick={() => navigate('/cadastro')}
            style={{ color: 'var(--primary)', cursor: 'pointer', fontWeight: '600' }}
          >
            Cadastre-se
          </span>
        </div>
      </div>
    </div>
  );
}