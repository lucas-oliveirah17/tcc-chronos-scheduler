import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { profissionalService } from '../../services/profissionalService';
import { ProfissionalForm } from '../../components/ProfissionalForm';

export function ProfissionalEditPage() {
  const [profissional, setProfissional] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProfissional = async () => {
      try {
        const data = await profissionalService.getProfissionalById(id);
        setProfissional(data);
      } catch (error) {
        console.error("Erro ao buscar profissional:", error);
        navigate('/admin/profissionais');
      }
    };
    fetchProfissional();
  }, [id, navigate]);

  const handleUpdate = async (formData) => {
    try {
      await profissionalService.updateProfissional(id, formData);
      navigate('/admin/profissionais');
    } catch (error) {
      console.error("Erro ao atualizar profissional:", error);
      alert("Falha ao atualizar profissional.");
    }
  };

  if (!profissional) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Editar Profissional: {profissional.usuario.nome}</h2>
      <ProfissionalForm 
        onSubmit={handleUpdate} 
        dadosIniciais={profissional} 
        isEdit={true} 
      />
    </div>
  );
}