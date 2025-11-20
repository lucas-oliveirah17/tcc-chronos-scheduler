import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { TabelaModular } from '../../components/TabelaModular';

export function ClientesPage() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchClientes = async () => {
      try {
        // Assumindo que existe um endpoint ou filtro para clientes
        // Se não houver, filtramos no front:
        const todosUsuarios = await usuarioService.getAllUsuarios();
        const apenasClientes = todosUsuarios.filter(u => u.perfil === 'CLIENTE');
        setClientes(apenasClientes);
      } catch (error) {
        console.error("Erro ao buscar clientes:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchClientes();
  }, []);

  const handleEdit = (id) => navigate(`/admin/clientes/editar/${id}`);

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este cliente?')) {
      try {
        await usuarioService.deleteUsuario(id);
        setClientes(clientes.filter(c => c.id !== id));
      } catch (error) {
        console.error("Erro ao excluir cliente:", error);
        alert('Erro ao excluir cliente');
      }
    }
  };

  const colunasMapeadas = {
    'Nome': 'nome',
    'Email': 'email',
    'Telefone': 'telefone'
  };

  if (loading) {
    return <div className="admin-page-container"><p>Carregando...</p></div>;
  }

  return (
    <div className="admin-page-container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2 style={{ margin: 0 }}>Gestão de Clientes</h2>
        {/* Botão de adicionar cliente se necessário */}
      </div>
      <TabelaModular
        colunasMapeadas={colunasMapeadas}
        dados={clientes}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
    </div>
  );
}