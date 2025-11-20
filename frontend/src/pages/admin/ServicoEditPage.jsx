import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { servicoService } from '../../services/servicoService';
import { ServicoForm } from '../../components/ServicoForm';

export function ServicoEditPage() {
  const [servico, setServico] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchServico = async () => {
      try {
        const data = await servicoService.getServicoById(id);
        setServico(data);
      } catch (error) {
        console.error("Erro ao buscar serviço:", error);
        navigate('/admin/servicos');
      }
    };
    fetchServico();
  }, [id, navigate]);

  const handleUpdate = async (formData) => {
    try {
      await servicoService.updateServico(id, formData);
      navigate('/admin/servicos');
    } catch (error) {
      console.error("Erro ao atualizar serviço:", error);
      alert("Falha ao atualizar serviço.");
    }
  };

  if (!servico) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Editar Serviço: {servico.nome}</h2>
      <ServicoForm 
        onSubmit={handleUpdate} 
        dadosIniciais={servico} 
        isEdit={true} 
      />
    </div>
  );
}