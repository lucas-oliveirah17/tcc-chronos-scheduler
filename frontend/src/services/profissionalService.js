import api from './api';

const createProfissional = async (profissionalRequestDTO) => {
  const response = await api.post('/profissionais', profissionalRequestDTO);
  return response.data;
};

const getAllProfissionais = async () => {
  const response = await api.get('/profissionais');
  
  if (response.data && response.data.content) {
    return response.data.content; 
  }

  if (Array.isArray(response.data)) {
      return response.data;
  }

  console.warn("API /profissionais não retornou um array ou um objeto { content: [...] }");
  return [];
};


const getProfissionalById = async (id) => {
  const response = await api.get(`/profissionais/${id}`);
  return response.data;
};

const updateProfissional = async (id, profissionalUpdateDTO) => {
  const response = await api.put(`/profissionais/${id}`, profissionalUpdateDTO);
  return response.data;
};

const deleteProfissional = async (id) => {
  await api.delete(`/profissionais/${id}`);
};

export const profissionalService = {
  createProfissional,
  getAllProfissionais, // Nossa função corrigida
  getProfissionalById,
  updateProfissional,
  deleteProfissional
};