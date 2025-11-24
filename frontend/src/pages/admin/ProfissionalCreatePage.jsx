import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { profissionalService } from '../../services/profissionalService';
import { usuarioService } from '../../services/usuarioService';
import { User, Briefcase, UserPlus, ArrowLeft} from 'lucide-react';

export function ProfissionalCreatePage() {
  const navigate = useNavigate();
  const [saving, setSaving] = useState(false);
  const [loadingUsers, setLoadingUsers] = useState(true);

  const [usuariosDisponiveis, setUsuariosDisponiveis] = useState([]);
  
  const [formData, setFormData] = useState({
    usuarioId: '',
    especialidades: ''
  });

  useEffect(() => {
    async function loadUsers() {
      try{
        const todosUsuarios = await usuarioService.getAllUsuarios();

        const profissionaisExistentes = await profissionalService.getAllProfissionais();

        const idsJaVinculados = new Set(profissionaisExistentes.map(p => p.usuario.id));

        const disponiveis = todosUsuarios.filter(u =>
          u.perfil === 'PROFISSIONAL' && !idsJaVinculados.has(u.id)
        );

        setUsuariosDisponiveis(disponiveis);

      } catch(error) {
        console.error("Erro ao carregar usuários:", error);
        alert("Erro ao carregar lista de usuários disponíveis.");
      
      } finally {
        setLoadingUsers(false);
      }
    }
    loadUsers();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if(!formData.usuarioId) {
      alert("Por favor, selecione um usuário.");
      return;
    }

    setSaving(true);
    try {
      await profissionalService.createProfissional({
        usuarioId: Number(formData.usuarioId),
        especialidades: formData.especialidades
      });

      alert('Profissional vinculado com sucesso!');
      navigate('/admin/profissionais');

    } catch (error) {
      console.error("Erro ao criar profissional:", error);
      
      const msg = error.response?.data || 'Erro ao criar profissional';
      alert(msg);

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
              onClick={() => navigate('/admin/profissionais')}
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
            Novo Profissional
          </h2>
          <p style={{ color: 'var(--text-muted)' }}>Vincule um usuário existente como profissional</p>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit}>

          <div style={{ marginBottom: '1.5rem' }}>
            <label htmlFor="usuarioId" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <User size={16} />
              Selecionar Usuário (Perfil Profissional)
            </label>

            <div style={{ display: 'flex', gap: '0.5rem' }}>
              <select
                id="usuarioId"
                name="usuarioId"
                value={formData.usuarioId}
                onChange={handleChange}
                required
                style={{ flex: 1, padding: '0.75rem', borderRadius: '8px', border: '1px solid #ccc', background: 'var(--bg-input)', color: 'var(--text-main)' }}
                disabled={loadingUsers}
              >
                <option value="">Selecione um usuário...</option>
                {usuariosDisponiveis.map(usuario => (
                  <option key={usuario.id} value={usuario.id}>
                    {usuario.nome} ({usuario.email})
                  </option>
                ))}
              </select>
            </div>

            <div style={{ marginTop: '0.5rem', fontSize: '0.85rem', color: 'var(--text-muted)' }}>
              Não encontrou?{' '}
              <span 
                onClick={() => navigate('/admin/usuarios/novo')}
                style={{ color: 'var(--primary)', cursor: 'pointer', textDecoration: 'underline' }}
              >
                Cadastrar novo usuário
              </span>
            </div>

            {loadingUsers && <p style={{ fontSize: '0.8rem', color: '#aaa' }}>Carregando usuários...</p>}
            {!loadingUsers && usuariosDisponiveis.length === 0 && (
              <p style={{ fontSize: '0.8rem', color: '#ff6b6b', marginTop: '0.5rem' }}>
                Nenhum usuário com perfil 'PROFISSIONAL' disponível. Cadastre um primeiro.
              </p>
            )}
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <label htmlFor="especialidades" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Briefcase size={16} />
              Especialidades
            </label>
            <input
              type="text"
              id="especialidades"
              name="especialidades"
              placeholder="Ex: Corte de Cabelo, Manicure..."
              value={formData.especialidades}
              onChange={handleChange}
              style={{ width: '100%', padding: '0.75rem', borderRadius: '8px', border: '1px solid #ccc', background: 'var(--bg-input)', color: 'var(--text-main)' }}
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary"
            style={{ width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '0.5rem' }}
            disabled={saving || (usuariosDisponiveis.length === 0 && !formData.usuarioId)}
          >
            {saving ? 'Vinculando...' : <><UserPlus size={18} /> Criar Profissional</>}
          </button>
        </form>
      </div>
    </div>
  );
}