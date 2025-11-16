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
        const data = await usuarioService.getAllClientes();
        setClientes(data || []);
      } catch (error) {
        console.error("Erro ao buscar clientes:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchClientes();
  }, []); 

  const handleEdit = (id) => {
    navigate(`/admin/clientes/editar/${id}`);
  };

  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja deletar este cliente?")) {
      try {
        await usuarioService.deleteUsuario(id);
        setClientes(clientes.filter(c => c.id !== id));
      } catch (error) {
        console.error("Erro ao deletar cliente:", error);
        alert("Falha ao deletar cliente.");
      }
    }
  };
  
  const colunasMapeadas = {
    'Nome': 'nome',
    'Email': 'email',
    'Telefone': 'telefone'
  };

  if (loading) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Gestão de Clientes</h2>
      {/* Não há botão de "Adicionar", pois o cadastro é público */}
      <TabelaModular 
        colunasMapeadas={colunasMapeadas}
        dados={clientes} 
        onEdit={handleEdit} 
        onDelete={handleDelete}
      /> 
    </div>
  );
}