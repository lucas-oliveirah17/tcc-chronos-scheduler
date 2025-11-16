import React, { useState, useEffect } from 'react';

export function ServicoForm({ dadosIniciais = {}, onSubmit, isEdit = false }) {
  const [formData, setFormData] = useState({
    nome: '',
    descricao: '',
    duracaoMinutos: 0,
    preco: 0.0
  });

  useEffect(() => {
    if (isEdit && dadosIniciais) {
      setFormData({
        nome: dadosIniciais.nome || '',
        descricao: dadosIniciais.descricao || '',
        duracaoMinutos: dadosIniciais.duracaoMinutos || 0,
        preco: dadosIniciais.preco || 0.0
      });
    }
  }, [dadosIniciais, isEdit]);

  const handleChange = (e) => {
    const { name, value, type } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'number' ? parseFloat(value) : value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="admin-form">
      <div className="form-group">
        <label htmlFor="nome">Nome do Serviço</label>
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
        <label htmlFor="descricao">Descrição</label>
        <textarea
          id="descricao"
          name="descricao"
          value={formData.descricao}
          onChange={handleChange}
        />
      </div>
      <div className="form-group">
        <label htmlFor="duracaoMinutos">Duração (Minutos)</label>
        <input
          type="number"
          id="duracaoMinutos"
          name="duracaoMinutos"
          value={formData.duracaoMinutos}
          onChange={handleChange}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="preco">Preço (R$)</label>
        <input
          type="number"
          step="0.01"
          id="preco"
          name="preco"
          value={formData.preco}
          onChange={handleChange}
          required
        />
      </div>
      <button type="submit" className="btn-salvar">
        {isEdit ? 'Atualizar' : 'Cadastrar'}
      </button>
    </form>
  );
}