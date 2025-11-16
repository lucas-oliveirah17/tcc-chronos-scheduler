import React from 'react';
import { useNavigate } from 'react-router-dom';
import { servicoService } from '../../services/servicoService';
import { ServicoForm } from '../../components/ServicoForm';

export function ServicoCreatePage() {
  const navigate = useNavigate();

  const handleCreate = async (formData) => {
    try {
      await servicoService.createServico(formData);
      navigate('/admin/servicos'); 
    } catch (error) {
      console.error("Erro ao criar serviço:", error);
      alert("Falha ao criar serviço.");
    }
  };

  return (
    <div className="admin-page-container">
      <h2>Cadastrar Novo Serviço</h2>
      <ServicoForm onSubmit={handleCreate} isEdit={false} />
    </div>
  );
}