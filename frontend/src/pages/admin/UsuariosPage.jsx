import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { TabelaModular } from '../../components/TabelaModular'; 

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
    navigate(`/admin/usuarios/editar/${id}`);
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
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Gestão de Usuários</h2>
      <button onClick={() => navigate('/admin/usuarios/novo')} className="btn-adicionar">
        Adicionar Novo Usuário
      </button>
      <TabelaModular 
        colunasMapeadas={colunasMapeadas}
        dados={usuarios} 
        onEdit={handleEdit} 
        onDelete={handleDelete}
      /> 
    </div>
  );
}