import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { servicoService } from '../../services/servicoService';
import { Scissors, DollarSign, Clock, Plus, ArrowLeft } from 'lucide-react';

export function ServicoCreatePage() {
  const navigate = useNavigate();
  const [saving, setSaving] = useState(false);
  const [formData, setFormData] = useState({
    nome: '',
    descricao: '',
    preco: '',
    duracao: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      const payload = {
        ...formData,
        preco: parseFloat(formData.preco),
        duracao: parseInt(formData.duracao, 10)
      };
      await servicoService.createServico(payload);
      alert('Serviço criado com sucesso!');
      navigate('/admin/servicos');
    } catch (error) {
      console.error("Erro ao criar serviço:", error);
      alert('Erro ao criar serviço');
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="container" style={{ minHeight: '80vh', display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '2rem 1rem' }}>
      <div className="card" style={{ maxWidth: '500px', width: '100%', backdropFilter: 'blur(10px)', backgroundColor: 'rgba(30, 41, 59, 0.7)' }}>
        {/* Header */}
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1rem' }}>
            <button
              onClick={() => navigate('/admin/servicos')}
              className="btn-icon"
              style={{ background: 'rgba(100, 116, 139, 0.2)', padding: '0.5rem', borderRadius: '8px' }}
              title="Voltar"
            >
              <ArrowLeft size={20} color="var(--text-main)" />
            </button>
            <div style={{ flex: 1 }} />
          </div>
          <h2 style={{
            fontSize: '2rem',
            marginBottom: '0.5rem',
            background: 'linear-gradient(135deg, #00C6FF 0%, #0072FF 100%)',
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent'
          }}>
            Novo Serviço
          </h2>
          <p style={{ color: 'var(--text-muted)' }}>Cadastre um novo serviço no sistema</p>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="nome" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Scissors size={16} />
              Nome do Serviço
            </label>
            <input
              type="text"
              id="nome"
              name="nome"
              placeholder="Ex: Corte de Cabelo, Manicure..."
              value={formData.nome}
              onChange={handleChange}
              required
            />
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="descricao">Descrição</label>
            <textarea
              id="descricao"
              name="descricao"
              placeholder="Descreva o serviço..."
              value={formData.descricao}
              onChange={handleChange}
              rows="3"
              style={{ resize: 'vertical' }}
            />
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="preco" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <DollarSign size={16} />
              Preço (R$)
            </label>
            <input
              type="number"
              id="preco"
              name="preco"
              placeholder="0.00"
              value={formData.preco}
              onChange={handleChange}
              step="0.01"
              min="0"
              required
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <label htmlFor="duracao" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Clock size={16} />
              Duração (minutos)
            </label>
            <input
              type="number"
              id="duracao"
              name="duracao"
              placeholder="Ex: 30, 60..."
              value={formData.duracao}
              onChange={handleChange}
              min="1"
              required
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary"
            style={{ width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '0.5rem' }}
            disabled={saving}
          >
            {saving ? 'Criando...' : <><Plus size={18} /> Criar Serviço</>}
          </button>
        </form>
      </div>
    </div>
  );
}