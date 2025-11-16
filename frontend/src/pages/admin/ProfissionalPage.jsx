import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { profissionalService } from '../../services/profissionalService';
import { TabelaModular } from '../../components/TabelaModular'; 

export function ProfissionaisPage() {
  const [profissionais, setProfissionais] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProfissionais = async () => {
      try {
        const data = await profissionalService.getAllProfissionais();
        setProfissionais(data || []); // Garante que é um array
      } catch (error) {
        console.error("Erro ao buscar profissionais:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProfissionais();
  }, []); 

  const handleEdit = (id) => {
    navigate(`/admin/profissionais/editar/${id}`);
  };

  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja deletar este profissional?")) {
      try {
        await profissionalService.deleteProfissional(id);
        setProfissionais(profissionais.filter(p => p.id !== id));
      } catch (error) {
        console.error("Erro ao deletar profissional:", error);
        alert("Falha ao deletar profissional.");
      }
    }
  };
  
  const colunasMapeadas = {
    'Nome': 'nome',
    'Email': 'email',
    'Especialidades': 'especialidades'
  };
  
  // ==================================================
  //           MUDANÇA ESTÁ AQUI
  // ==================================================
  const dadosFormatados = profissionais.map(p => ({
    id: p.id,
    
    // Verifica se p.usuario existe antes de tentar acessar .nome ou .email
    nome: p.usuario?.nome || 'Usuário Inválido',
    email: p.usuario?.email || 'N/A',
    
    especialidades: p.especialidades || 'N/A'
  }));
  // ==================================================

  if (loading) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Gestão de Profissionais</h2>
      <button onClick={() => navigate('/admin/profissionais/novo')} className="btn-adicionar">
        Adicionar Novo Profissional
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