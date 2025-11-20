import React, { useState, useEffect } from 'react';

export function UsuarioForm({ dadosIniciais = {}, onSubmit, isEdit = false }) {
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    telefone: '',
    senha: '',
    perfil: 'CLIENTE'
  });

  useEffect(() => {
    if (isEdit && dadosIniciais) {
      setFormData({
        nome: dadosIniciais.nome || '',
        email: dadosIniciais.email || '',
        telefone: dadosIniciais.telefone || '',
        senha: '',
        perfil: dadosIniciais.perfil || 'CLIENTE'
      });
    }
  }, [dadosIniciais, isEdit]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isEdit) {
      const { nome, email, telefone, perfil } = formData;
      onSubmit({ nome, email, telefone, perfil });
    } else {
      onSubmit(formData);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="admin-form">
      <div className="form-group">
        <label htmlFor="nome">Nome Completo</label>
        <input
          type="text"
          id="nome"
          name="nome"
          value={formData.nome}
          onChange={handleChange}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="telefone">Telefone</label>
        <input
          type="tel"
          id="telefone"
          name="telefone"
          value={formData.telefone}
          onChange={handleChange}
        />
      </div>

      {!isEdit && (
        <>
          <div className="form-group">
            <label htmlFor="senha">Senha</label>
            <input
              type="password"
              id="senha"
              name="senha"
              value={formData.senha}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="perfil">Perfil do Usuário</label>
            <select id="perfil" name="perfil" value={formData.perfil} onChange={handleChange} required>
              <option value="CLIENTE">Cliente</option>
              <option value="PROFISSIONAL">Profissional</option>
              <option value="ADMINISTRADOR">Administrador</option>
            </select>
          </div>
        </>
      )}

      <button type="submit" className="btn-salvar">
        {isEdit ? 'Atualizar Usuário' : 'Cadastrar Usuário'}
      </button>
    </form>
  );
}