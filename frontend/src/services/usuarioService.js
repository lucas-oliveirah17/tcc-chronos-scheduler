import api from './api';

const createUsuario = async (usuarioCreateDTO) => {
  const response = await api.post('/usuarios', usuarioCreateDTO);
  return response.data;
};

const getAllUsuarios = async () => {
  const response = await api.get('/usuarios');
  
  if (response.data && response.data.content) {
    return response.data.content;
  }
  if (Array.isArray(response.data)) {
      return response.data;
  }
  console.warn("API /usuarios não retornou um array ou um objeto { content: [...] }");
  return [];
};

const getAllClientes = async () => {
    const response = await api.get('/usuarios');
    let data = [];

    if (response.data && response.data.content) {
        data = response.data.content;
    } else if (Array.isArray(response.data)) {
        data = response.data;
    } else {
        console.warn("API /usuarios não retornou um array ou um objeto { content: [...] }");
        return [];
    }
    
    return data.filter(u => u.perfil === 'CLIENTE');
};

const getUsuarioById = async (id) => {
  const response = await api.get(`/usuarios/${id}`);
  return response.data;
};

const updateUsuario = async (id, usuarioUpdateDTO) => {
  const response = await api.put(`/usuarios/${id}`, usuarioUpdateDTO);
  return response.data;
};

const deleteUsuario = async (id) => {
  await api.delete(`/usuarios/${id}`);
};

export const usuarioService = {
  createUsuario,
  getAllUsuarios,
  getAllClientes,
  getUsuarioById,
  updateUsuario,
  deleteUsuario
};