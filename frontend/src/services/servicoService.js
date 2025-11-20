import api from './api';

const createServico = async (servicoRequestDTO) => {
  const response = await api.post('/servicos', servicoRequestDTO);
  return response.data;
};

const getAllServicos = async () => {
  const response = await api.get('/servicos');
  
  if (response.data && response.data.content) {
    return response.data.content;
  }
  if (Array.isArray(response.data)) {
      return response.data;
  }
  console.warn("API /servicos não retornou um array ou um objeto { content: [...] }");
  return [];
};

const getServicoById = async (id) => {
  const response = await api.get(`/servicos/${id}`);
  return response.data;
};

const updateServico = async (id, servicoRequestDTO) => {
  const response = await api.put(`/servicos/${id}`, servicoRequestDTO);
  return response.data;
};

const deleteServico = async (id) => {
  await api.delete(`/servicos/${id}`);
};

export const servicoService = {
  createServico,
  getAllServicos,
  getServicoById,
  updateServico,
  deleteServico
};