import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { profissionalService } from '../../services/profissionalService';
import { TabelaModular } from '../../components/TabelaModular';
import { Plus } from 'lucide-react';

export function ProfissionaisPage() {
  const [profissionais, setProfissionais] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProfissionais = async () => {
      try {
        const data = await profissionalService.getAllProfissionais();
        setProfissionais(data || []);
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

  const dadosFormatados = profissionais.map(p => ({
    id: p.id,
    nome: p.usuario?.nome || 'Usuário Inválido',
    email: p.usuario?.email || 'N/A',
    especialidades: p.especialidades || 'N/A'
  }));

  if (loading) {
    return <div className="admin-page-container"><p>Carregando...</p></div>;
  }

  return (
    <div className="admin-page-container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2 style={{ margin: 0 }}>Gestão de Profissionais</h2>
        <button onClick={() => navigate('/admin/profissionais/novo')} className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <Plus size={18} /> Novo Profissional
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