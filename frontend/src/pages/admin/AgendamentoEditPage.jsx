import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { agendamentoService } from '../../services/agendamentoService';
import { AgendamentoForm } from '../../components/AgendamentoForm';

export function AgendamentoEditPage() {
  const [agendamento, setAgendamento] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAgendamento = async () => {
      try {
        const data = await agendamentoService.getAgendamentoById(id);
        setAgendamento(data);
      } catch (error) {
        console.error("Erro ao buscar agendamento:", error);
        navigate('/admin/agendamentos');
      }
    };
    fetchAgendamento();
  }, [id, navigate]);

  const handleUpdate = async (formData) => {
    try {
      await agendamentoService.updateAgendamento(id, formData);
      navigate('/admin/agendamentos');
    } catch (error) {
      console.error("Erro ao atualizar agendamento:", error);
      alert("Falha ao atualizar agendamento.");
    }
  };

  if (!agendamento) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Editar Agendamento (ID: {agendamento.id})</h2>
      <AgendamentoForm 
        onSubmit={handleUpdate} 
        dadosIniciais={agendamento} 
        isEdit={true} 
      />
    </div>
  );
}