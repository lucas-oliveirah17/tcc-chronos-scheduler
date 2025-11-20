import React, { useState, useEffect } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { UserCog, Mail, Phone, Save, ArrowLeft } from 'lucide-react';

export function UsuarioEditPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const [usuario, setUsuario] = useState(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    telefone: '',
    perfil: 'CLIENTE'
  });

  useEffect(() => {
    // Try to get user data from location state first (workaround for 403)
    if (location.state && location.state.usuario) {
      const userData = location.state.usuario;
      setUsuario(userData);
      setFormData({
        nome: userData.nome || '',
        email: userData.email || '',
        telefone: userData.telefone || '',
        perfil: userData.perfil || 'CLIENTE'
      });
      setLoading(false);
    } else {
      // Fallback to API fetch
      const fetchUsuario = async () => {
        try {
          const data = await usuarioService.getUsuarioById(id);
          setUsuario(data);
          setFormData({
            nome: data.nome || '',
            email: data.email || '',
            telefone: data.telefone || '',
            perfil: data.perfil || 'CLIENTE'
          });
        } catch (error) {
          console.error("Erro ao buscar usuário:", error);
          alert('Erro ao carregar dados do usuário');
        } finally {
          setLoading(false);
        }
      };
      fetchUsuario();
    }
  }, [id, location.state]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      const payload = { ...formData };
      if (usuario?.id) {
        payload.id = usuario.id;
      }
      await usuarioService.updateUsuario(id, payload);
      alert('Usuário atualizado com sucesso!');
      navigate('/admin/usuarios');
    } catch (error) {
      console.error("Erro ao atualizar usuário:", error);
      alert('Erro ao atualizar usuário');
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="container" style={{ minHeight: '80vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <p style={{ color: 'var(--text-main)' }}>Carregando...</p>
      </div>
    );
  }

  return (
    <div className="container" style={{ minHeight: '80vh', display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '2rem 1rem' }}>
      <div className="card" style={{ maxWidth: '500px', width: '100%', backdropFilter: 'blur(10px)', backgroundColor: 'rgba(30, 41, 59, 0.7)' }}>
        {/* Header */}
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1rem' }}>
            <button
              onClick={() => navigate('/admin/usuarios')}
              className="btn-icon"
              style={{ background: 'rgba(100, 116, 139, 0.2)', padding: '0.5rem', borderRadius: '8px' }}
              title="Voltar"
            >
              <ArrowLeft size={20} color="var(--text-main)" />
            </button>
            <div style={{ flex: 1 }} />
          </div>
          <h2 style={{
            fontSize: '2rem',
            marginBottom: '0.5rem',
            background: 'linear-gradient(135deg, #00C6FF 0%, #0072FF 100%)',
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent'
          }}>
            Editar Usuário
          </h2>
          <p style={{ color: 'var(--text-muted)' }}>Atualize as informações do usuário</p>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="nome" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <UserCog size={16} />
              Nome Completo
            </label>
            <input
              type="text"
              id="nome"
              name="nome"
              placeholder="Digite o nome completo"
              value={formData.nome}
              onChange={handleChange}
              required
            />
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="email" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Mail size={16} />
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="usuario@example.com"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="telefone" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Phone size={16} />
              Telefone
            </label>
            <input
              type="tel"
              id="telefone"
              name="telefone"
              placeholder="(00) 00000-0000"
              value={formData.telefone}
              onChange={handleChange}
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <label htmlFor="perfil">Perfil do Usuário</label>
            <select
              id="perfil"
              name="perfil"
              value={formData.perfil}
              onChange={handleChange}
              required
            >
              <option value="CLIENTE">Cliente</option>
              <option value="PROFISSIONAL">Profissional</option>
              <option value="ADMINISTRADOR">Administrador</option>
            </select>
          </div>

          <button
            type="submit"
            className="btn btn-primary"
            style={{ width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '0.5rem' }}
            disabled={saving}
          >
            {saving ? 'Salvando...' : <><Save size={18} /> Atualizar Usuário</>}
          </button>
        </form>
      </div>
    </div>
  );
}