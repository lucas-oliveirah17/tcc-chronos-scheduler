import React, { useState, useEffect } from 'react';
import { usuarioService } from '../services/usuarioService';
import { profissionalService } from '../services/profissionalService';
import { servicoService } from '../services/servicoService';

export function AgendamentoForm({ dadosIniciais = {}, onSubmit, isEdit = false }) {
  const [formData, setFormData] = useState({
    clienteId: '',
    profissionalId: '',
    servicoId: '',
    dataHoraInicio: ''
  });

  const [clientes, setClientes] = useState([]);
  const [profissionais, setProfissionais] = useState([]);
  const [servicos, setServicos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (isEdit && dadosIniciais) {
      setFormData({
        clienteId: (dadosIniciais.cliente && dadosIniciais.cliente.id) || '',
        profissionalId: (dadosIniciais.profissional && dadosIniciais.profissional.id) || '',
        servicoId: (dadosIniciais.servico && dadosIniciais.servico.id) || '',
        dataHoraInicio: dadosIniciais.dataHoraInicio ? dadosIniciais.dataHoraInicio.substring(0, 16) : ''
      });
    }
  }, [dadosIniciais, isEdit]);

  useEffect(() => {
    const fetchDropdownData = async () => {
      setLoading(true);
      try {
        const [clientesData, profissionaisData, servicosData] = await Promise.all([
          usuarioService.getAllClientes(),
          profissionalService.getAllProfissionais(),
          servicoService.getAllServicos()
        ]);
        setClientes(clientesData || []);
        setProfissionais(profissionaisData || []);
        setServicos(servicosData || []);
      } catch (error) {
        console.error("Erro ao buscar dados para o formulário:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchDropdownData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const dataToSend = {
      ...formData,
      clienteId: parseInt(formData.clienteId, 10),
      profissionalId: parseInt(formData.profissionalId, 10),
      servicoId: parseInt(formData.servicoId, 10),
      dataHoraInicio: `${formData.dataHoraInicio}:00`
    };
    onSubmit(dataToSend);
  };
  
  if (loading) {
    return <p>Carregando dados do formulário...</p>;
  }

  return (
    <form onSubmit={handleSubmit} className="admin-form">
      <div className="form-group">
        <label htmlFor="clienteId">Cliente</label>
        <select id="clienteId" name="clienteId" value={formData.clienteId} onChange={handleChange} required>
          <option value="">Selecione um cliente</option>
          {clientes.map(cli => (
            <option key={cli.id} value={cli.id}>{cli.nome}</option>
          ))}
        </select>
      </div>
      
      <div className="form-group">
        <label htmlFor="profissionalId">Profissional</label>
        <select id="profissionalId" name="profissionalId" value={formData.profissionalId} onChange={handleChange} required>
          <option value="">Selecione um profissional</option>
          {profissionais.map(prof => (
            <option key={prof.id} value={prof.id}>{prof.usuario.nome}</option>
          ))}
        </select>
      </div>

      <div className="form-group">
        <label htmlFor="servicoId">Serviço</label>
        <select id="servicoId" name="servicoId" value={formData.servicoId} onChange={handleChange} required>
          <option value="">Selecione um serviço</option>
          {servicos.map(serv => (
            <option key={serv.id} value={serv.id}>{serv.nome}</option>
          ))}
        </select>
      </div>

      <div className="form-group">
        <label htmlFor="dataHoraInicio">Data e Hora</label>
        <input
          type="datetime-local"
          id="dataHoraInicio"
          name="dataHoraInicio"
          value={formData.dataHoraInicio}
          onChange={handleChange}
          required
        />
      </div>

      <button type="submit" className="btn-salvar">
        {isEdit ? 'Atualizar Agendamento' : 'Criar Agendamento'}
      </button>
    </form>
  );
}