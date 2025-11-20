import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { TabelaModular } from '../../components/TabelaModular';
import { Plus } from 'lucide-react';

export function UsuariosPage() {
  const [usuarios, setUsuarios] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        const data = await usuarioService.getAllUsuarios();
        setUsuarios(data || []);
      } catch (error) {
        console.error("Erro ao buscar usuários:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchUsuarios();
  }, []);

  const handleEdit = (id) => {
    const usuarioToEdit = usuarios.find(u => u.id === id);
    navigate(`/admin/usuarios/editar/${id}`, { state: { usuario: usuarioToEdit } });
  };

  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja deletar este usuário?")) {
      try {
        await usuarioService.deleteUsuario(id);
        setUsuarios(usuarios.filter(u => u.id !== id));
      } catch (error) {
        console.error("Erro ao deletar usuário:", error);
        alert("Falha ao deletar usuário.");
      }
    }
  };

  const colunasMapeadas = {
    'Nome': 'nome',
    'Email': 'email',
    'Telefone': 'telefone',
    'Perfil': 'perfil'
  };

  if (loading) {
    return <div className="admin-page-container"><p>Carregando...</p></div>;
  }

  return (
    <div className="admin-page-container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2 style={{ margin: 0 }}>Gestão de Usuários</h2>
        <button onClick={() => navigate('/admin/usuarios/novo')} className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <Plus size={18} /> Novo Usuário
        </button>
      </div>

      <TabelaModular
        colunasMapeadas={colunasMapeadas}
        dados={usuarios}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
    </div>
  );
}