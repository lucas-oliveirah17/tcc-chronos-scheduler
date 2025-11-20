import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { servicoService } from '../../services/servicoService';
import { TabelaModular } from '../../components/TabelaModular';
import { Plus } from 'lucide-react';

export function ServicosPage() {
  const [servicos, setServicos] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchServicos = async () => {
      try {
        const data = await servicoService.getAllServicos();
        setServicos(data || []);
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
    return <div className="admin-page-container"><p>Carregando...</p></div>;
  }

  return (
    <div className="admin-page-container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2 style={{ margin: 0 }}>Gestão de Serviços</h2>
        <button onClick={() => navigate('/admin/servicos/novo')} className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <Plus size={18} /> Novo Serviço
        </button>
      </div>
      <TabelaModular
        colunasMapeadas={colunasMapeadas}
        dados={dadosFormatados}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
    </div>
  );
}