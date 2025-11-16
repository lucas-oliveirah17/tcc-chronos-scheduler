import React from 'react';
import { useNavigate } from 'react-router-dom';
import { profissionalService } from '../../services/profissionalService';
import { ProfissionalForm } from '../../components/ProfissionalForm';

export function ProfissionalCreatePage() {
  const navigate = useNavigate();

  const handleCreate = async (formData) => {
    try {
      await profissionalService.createProfissional(formData);
      navigate('/admin/profissionais'); 
    } catch (error) {
      console.error("Erro ao criar profissional:", error);
      alert("Falha ao criar profissional.");
    }
  };

  return (
    <div className="admin-page-container">
      <h2>Cadastrar Novo Profissional</h2>
      <ProfissionalForm onSubmit={handleCreate} isEdit={false} />
    </div>
  );
}