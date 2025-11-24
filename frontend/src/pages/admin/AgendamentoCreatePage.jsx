import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { agendamentoService } from '../../services/agendamentoService';

import { profissionalService } from '../../services/profissionalService';
import { servicoService } from '../../services/servicoService';
import { Calendar, Clock, ShoppingBag, Briefcase, Scissors, Plus, ArrowLeft } from 'lucide-react';

export function AgendamentoCreatePage() {
  const navigate = useNavigate();
  const [saving, setSaving] = useState(false);

  const [profissionais, setProfissionais] = useState([]);
  const [servicos, setServicos] = useState([]);
  const [loading, setLoading] = useState(true);

  const [formData, setFormData] = useState({
    dataHoraInicio: '',
    clienteId: '',
    profissionalId: '',
    servicoId: ''
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [profissionaisData, servicosData] = await Promise.all([
          profissionalService.getAllProfissionais(),
          servicoService.getAllServicos()
        ]);

        setProfissionais(profissionaisData);
        setServicos(servicosData);
      } catch (error) {
        console.error("Erro ao carregar dados:", error);
        alert('Erro ao carregar dados necessários');
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      // Ensure seconds are included in the timestamp
      const dataHoraFormatted = formData.dataHoraInicio.length === 16
        ? `${formData.dataHoraInicio}:00`
        : formData.dataHoraInicio;

      const clienteIdInt = parseInt(formData.clienteId, 10);
      const profissionalIdInt = parseInt(formData.profissionalId, 10);
      const servicoIdInt = parseInt(formData.servicoId, 10);

      if (isNaN(clienteIdInt) || isNaN(profissionalIdInt) || isNaN(servicoIdInt)) {
        alert("Por favor, preencha todos os campos corretamente.");
        setSaving(false);
        return;
      }

      const payload = {
        dataHoraInicio: dataHoraFormatted,
        clienteId: clienteIdInt,
        profissionalId: profissionalIdInt,
        servicoId: servicoIdInt
      };

      console.log("Enviando payload:", JSON.stringify(payload, null, 2));
      await agendamentoService.createAgendamento(payload);
      alert('Agendamento criado com sucesso!');
      navigate('/admin/agendamentos');
    } catch (error) {
      console.error("Erro ao criar agendamento:", error);
      alert('Erro ao criar agendamento');
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="container" style={{ minHeight: '80vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <p style={{ color: 'var(--text-main)' }}>Carregando...</p>
      </div>
    );
  }

  return (
    <div className="container" style={{ minHeight: '80vh', display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '2rem 1rem' }}>
      <div className="card" style={{ maxWidth: '500px', width: '100%', backdropFilter: 'blur(10px)', backgroundColor: 'rgba(30, 41, 59, 0.7)' }}>
        {/* Header */}
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1rem' }}>
            <button
              onClick={() => navigate('/admin/agendamentos')}
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
            Novo Agendamento
          </h2>
          <p style={{ color: 'var(--text-muted)' }}>Crie um novo agendamento no sistema</p>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="dataHoraInicio" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Calendar size={16} />
              Data e Hora
            </label>
            <input
              type="datetime-local"
              id="dataHoraInicio"
              name="dataHoraInicio"
              value={formData.dataHoraInicio}
              onChange={handleChange}
              required
            />
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="clienteId" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <ShoppingBag size={16} />
              Cliente
            </label>
            <input
              type="number"
              id="clienteId"
              name="clienteId"
              placeholder="ID do Cliente"
              value={formData.clienteId}
              onChange={handleChange}
              required
            />
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="profissionalId" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Briefcase size={16} />
              Profissional
            </label>
            <select
              id="profissionalId"
              name="profissionalId"
              value={formData.profissionalId}
              onChange={handleChange}
              required
            >
              <option value="">Selecione um profissional</option>
              {profissionais.map(prof => (
                <option key={prof.id} value={prof.id}>
                  {prof.usuario?.nome || `Profissional ${prof.id}`}
                </option>
              ))}
            </select>
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="servicoId" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.5rem' }}>
              <Scissors size={16} />
              Serviço
            </label>
            <select
              id="servicoId"
              name="servicoId"
              value={formData.servicoId}
              onChange={handleChange}
              required
            >
              <option value="">Selecione um serviço</option>
              {servicos.map(servico => (
                <option key={servico.id} value={servico.id}>
                  {servico.nome} - R$ {servico.preco}
                </option>
              ))}
            </select>
          </div>



          <button
            type="submit"
            className="btn btn-primary"
            style={{ width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '0.5rem' }}
            disabled={saving}
          >
            {saving ? 'Criando...' : <><Plus size={18} /> Criar Agendamento</>}
          </button>
        </form>
      </div>
    </div>
  );
}