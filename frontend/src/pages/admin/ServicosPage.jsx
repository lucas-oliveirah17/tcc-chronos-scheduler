import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { servicoService } from '../../services/servicoService';
import { TabelaModular } from '../../components/TabelaModular'; 

export function ServicosPage() {
  const [servicos, setServicos] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchServicos = async () => {
      try {
        const data = await servicoService.getAllServicos();
        setServicos(data || []); // Garante que é um array
      } catch (error) {
        console.error("Erro ao buscar serviços:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchServicos();
  }, []); 

  const handleEdit = (id) => {
    navigate(`/admin/servicos/editar/${id}`);
  };

  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja deletar?")) {
      try {
        await servicoService.deleteServico(id);
        setServicos(servicos.filter(s => s.id !== id));
      } catch (error) {
        console.error("Erro ao deletar serviço:", error);
        alert("Falha ao deletar serviço.");
      }
    }
  };
  
  const colunasMapeadas = {
    'Nome': 'nome',
    'Descrição': 'descricao',
    'Duração': 'duracaoMinutos',
    'Preço': 'preco'
  };
  

  const dadosFormatados = servicos.map(s => ({
    ...s,

    duracaoMinutos: s.duracaoMinutos ? `${s.duracaoMinutos} min` : 'N/A',
 
    preco: (typeof s.preco === 'number') ? `R$ ${s.preco.toFixed(2)}` : 'N/A'
  }));


  if (loading) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Gestão de Serviços</h2>
      <button onClick={() => navigate('/admin/servicos/novo')} className="btn-adicionar">
        Adicionar Novo Serviço
      </button>
      <TabelaModular 
        colunasMapeadas={colunasMapeadas}
        dados={dadosFormatados} 
        onEdit={handleEdit} 
        onDelete={handleDelete}
      /> 
    </div>
  );
}