import api from './api';

const createAgendamento = async (agendamentoRequestDTO) => {
  const response = await api.post('/agendamentos', agendamentoRequestDTO);
  return response.data;
};

const getAllAgendamentos = async () => {
  const response = await api.get('/agendamentos');
  
  if (response.data && response.data.content) {
    return response.data.content;
  }
  if (Array.isArray(response.data)) {
      return response.data;
  }
  console.warn("API /agendamentos não retornou um array ou um objeto { content: [...] }");
  return [];
};

const getAgendamentoById = async (id) => {
  const response = await api.get(`/agendamentos/${id}`);
  return response.data;
};

const updateAgendamento = async (id, agendamentoRequestDTO) => {
  const response = await api.put(`/agendamentos/${id}`, agendamentoRequestDTO);
  return response.data;
};

const deleteAgendamento = async (id) => {
  await api.delete(`/agendamentos/${id}`);
};

export const agendamentoService = {
  createAgendamento,
  getAllAgendamentos,
  getAgendamentoById,
  updateAgendamento,
  deleteAgendamento
};