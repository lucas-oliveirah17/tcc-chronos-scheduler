import React from 'react';
import { useNavigate } from 'react-router-dom';
import { agendamentoService } from '../../services/agendamentoService';
import { AgendamentoForm } from '../../components/AgendamentoForm';

export function AgendamentoCreatePage() {
  const navigate = useNavigate();

  const handleCreate = async (formData) => {
    try {
      await agendamentoService.createAgendamento(formData);
      navigate('/admin/agendamentos'); 
    } catch (error) {
      console.error("Erro ao criar agendamento:", error);
      alert("Falha ao criar agendamento.");
    }
  };

  return (
    <div className="admin-page-container">
      <h2>Criar Novo Agendamento</h2>
      <AgendamentoForm onSubmit={handleCreate} isEdit={false} />
    </div>
  );
}