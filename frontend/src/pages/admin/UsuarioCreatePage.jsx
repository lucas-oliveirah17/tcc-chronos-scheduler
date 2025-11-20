import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { UserCog, Mail, Phone, Lock, Shield, UserPlus, ArrowLeft } from 'lucide-react';

export function UsuarioCreatePage() {
  const navigate = useNavigate();
  const [saving, setSaving] = useState(false);
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    telefone: '',
    senha: '',
    perfil: 'CLIENTE'
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      await usuarioService.createUsuario(formData);
      alert('Usuário criado com sucesso!');
      navigate('/admin/usuarios');
    } catch (error) {
      console.error("Erro ao criar usuário:", error);
      alert('Erro ao criar usuário');
    } finally {
      setSaving(false);
    }
  };

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
            Novo Usuário
          </h2>
          <p style={{ color: 'var(--text-muted)' }}>Cadastre um novo usuário no sistema</p>
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

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="senha" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Lock size={16} />
              Senha
            </label>
            <input
              type="password"
              id="senha"
              name="senha"
              placeholder="••••••••"
              value={formData.senha}
              onChange={handleChange}
              required
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <label htmlFor="perfil" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Shield size={16} />
              Perfil do Usuário
            </label>
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
            {saving ? 'Criando...' : <><UserPlus size={18} /> Criar Usuário</>}
          </button>
        </form>
      </div>
    </div>
  );
}