import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { agendamentoService } from '../../services/agendamentoService';
import { TabelaModular } from '../../components/TabelaModular';
import { Plus } from 'lucide-react';

export function AgendamentosPage() {
  const [agendamentos, setAgendamentos] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAgendamentos = async () => {
      try {
        const data = await agendamentoService.getAllAgendamentos();
        setAgendamentos(data || []);
      } catch (error) {
        console.error("Erro ao buscar agendamentos:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchAgendamentos();
  }, []);



  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja cancelar/deletar este agendamento?")) {
      try {
        await agendamentoService.deleteAgendamento(id);
        setAgendamentos(agendamentos.filter(a => a.id !== id));
      } catch (error) {
        console.error("Erro ao deletar agendamento:", error);
        alert("Falha ao deletar agendamento.");
      }
    }
  };

  const colunasMapeadas = {
    'ID': 'id',
    'Cliente': 'clienteNome',
    'Profissional': 'profissionalNome',
    'Serviço': 'servicoNome',
    'Início': 'dataHoraInicio',
    'Status': 'status'
  };

  const formatDataHora = (isoString) => {
    if (!isoString) return 'N/A';
    try {
      const data = new Date(isoString);
      return data.toLocaleString('pt-BR');
    } catch (e) {
      return 'Data inválida';
    }
  };

  const dadosFormatados = agendamentos.map(a => ({
    id: a.id,
    clienteNome: (a.cliente && a.cliente.nome) ? a.cliente.nome : 'N/A',
    profissionalNome: (a.profissional && a.profissional.usuario && a.profissional.usuario.nome) ? a.profissional.usuario.nome : 'N/A',
    servicoNome: (a.servico && a.servico.nome) ? a.servico.nome : 'N/A',
    dataHoraInicio: formatDataHora(a.dataHoraInicio),
    status: a.status || 'N/A'
  }));

  if (loading) {
    return <div className="admin-page-container"><p>Carregando...</p></div>;
  }

  return (
    <div className="admin-page-container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2 style={{ margin: 0 }}>Gestão de Agendamentos</h2>
        <button onClick={() => navigate('/admin/agendamentos/novo')} className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <Plus size={18} /> Novo Agendamento
        </button>
      </div>
      <TabelaModular
        colunasMapeadas={colunasMapeadas}
        dados={dadosFormatados}
        onDelete={handleDelete}
      />
    </div>
  );
}