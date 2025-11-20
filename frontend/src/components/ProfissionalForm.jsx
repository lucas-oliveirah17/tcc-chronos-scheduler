import React, { useState, useEffect } from 'react';
import { usuarioService } from '../services/usuarioService';

export function ProfissionalForm({ dadosIniciais = {}, onSubmit, isEdit = false }) {
  const [formData, setFormData] = useState({
    especialidades: '',
    usuarioId: ''
  });
  
  const [usuarios, setUsuarios] = useState([]);
  const [loadingUsers, setLoadingUsers] = useState(false);

  useEffect(() => {
    if (isEdit && dadosIniciais) {
      setFormData({
        especialidades: dadosIniciais.especialidades || '',
        usuarioId: (dadosIniciais.usuario && dadosIniciais.usuario.id) ? dadosIniciais.usuario.id : ''
      });
    }
  }, [dadosIniciais, isEdit]);

  useEffect(() => {
    if (!isEdit) {
      const fetchUsuarios = async () => {
        setLoadingUsers(true);
        try {
          const data = await usuarioService.getAllUsuarios(); 
          
          if (Array.isArray(data)) {
            const usuariosProfissionais = data.filter(
              user => user.perfil === 'PROFISSIONAL'
            );
            setUsuarios(usuariosProfissionais);
          } else {
            console.error("fetchUsuarios (form): data não é um array.", data);
            setUsuarios([]);
          }

        } catch (error) {
          console.error("Erro ao buscar usuários:", error);
          setUsuarios([]);
        } finally {
          setLoadingUsers(false);
        }
      };
      fetchUsuarios();
    }
  }, [isEdit]);

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
      especialidades: formData.especialidades,
      usuarioId: parseInt(formData.usuarioId, 10)
    };

    if (isEdit) {
      onSubmit({ especialidades: formData.especialidades });
    } else {
      onSubmit(dataToSend);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="admin-form">
      <div className="form-group">
        <label htmlFor="especialidades">Especialidades</label>
        <input
          type="text"
          id="especialidades"
          name="especialidades"
          value={formData.especialidades}
          onChange={handleChange}
          required
        />
      </div>
      
      {!isEdit && (
        <div className="form-group">
          <label htmlFor="usuarioId">Usuário Vinculado</label>
          {loadingUsers ? (
            <p>Carregando usuários...</p>
          ) : (
            <select
              id="usuarioId"
              name="usuarioId"
              value={formData.usuarioId}
              onChange={handleChange}
              required
            >
              <option value="">Selecione um usuário profissional</option>
              {usuarios.map(user => (
                <option key={user.id} value={user.id}>
                  {user.nome} (Email: {user.email})
                </option>
              ))}
            </select>
          )}
        </div>
      )}

      <button type="submit" className="btn-salvar">
        {isEdit ? 'Atualizar Especialidades' : 'Cadastrar Profissional'}
      </button>
    </form>
  );
}