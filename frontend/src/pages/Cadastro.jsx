import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import './Cadastro.css';

export function Cadastro() {
  const navigate = useNavigate();

  // State para guardar os dados do formulário
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    telefone: '',
    senha: '',
  });

  // State separado para o "Repetir Senha"
  const [senhaRepeat, setSenhaRepeat] = useState('');

  // Função para atualizar o state a cada tecla digitada
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  }

  // Função para lidar com o envio do formulário
  const handleSubmit = async (e) => {
    e.preventDefault(); // Impede o recarregamento da página

    if (formData.senha !== senhaRepeat) {
      alert('Erro: As senhas não conferem. Por favor, digite novamente.');
      return;
    }

    try {
      const dadosParaEnviar = {
        nome: formData.nome,
        email: formData.email,
        senha: formData.senha,
        telefone: formData.telefone,
        perfil: 'CLIENTE' // Perfil fixo para cadastro de cliente
      };

      await api.post('/api/usuarios', dadosParaEnviar);

      alert('Cadastro realizado com sucesso! Você será redirecionado para o login.')
      navigate('/login');

    } catch (error) {
      console.error('Erro ao cadastrar:', error);
      if(error.response && error.response.data) {
        alert(`Erro: ${error.response.data}`);
      } else {
        alert('Ocorreu um erro ao tentar cadastrar.')
      }
    }
  }

  return (
    <form onSubmit={handleSubmit} style={{ border: "1px solid #ccc" }}>
      <div className="container">
        <h1>Cadastre-se</h1>
        <p>Preenchar este formulário para criar uma conta.</p>
        <hr />

        <div className="form-grid">
          {/* Coluna Esquerda */}
          <div>
            <label htmlFor="nome"><b>Nome</b></label>
            <input 
              type="text" 
              placeholder="Digite o seu nome" 
              name="nome" 
              id="nome" 
              value={formData.nome} 
              onChange={handleChange} 
              required 
            />

            <label htmlFor="senha"><b>Senha</b></label>
            <input 
              type="password" 
              placeholder="Digite a sua senha" 
              name="senha" 
              id="senha" 
              value={formData.senha} 
              onChange={handleChange} 
              required 
            />

            <label htmlFor="telefone"><b>Telefone</b></label>
            <input 
              type="tel" 
              placeholder="Digite o seu telefone" 
              name="telefone" 
              id="telefone" 
              value={formData.telefone} 
              onChange={handleChange}
              required
            />
          </div>

          {/* Coluna Direita */}
          <div>
            <label htmlFor="email"><b>E-mail</b></label>
            <input 
              type="email" 
              placeholder="Digite o seu e-mail" 
              name="email" value={formData.email} 
              id="email" 
              onChange={handleChange} 
              required 
            />

            <label htmlFor="senha-repeat"><b>Repita a senha</b></label>
            <input 
             type="password" 
             placeholder="Digite novamente a sua senha" 
             name="senha-repeat" 
             id="senha-repeat" 
             onChange={(e) => setSenhaRepeat(e.target.value)} 
             required 
            />
          </div>
        </div>

        <p>
          Ao criar uma conta, você concorda com nossos termos{" "}
          <a href="#" style={{ color: "dodgerblue" }}>
            Termos & Política de Privacidade
          </a>.
        </p>

        <div className="clearfix">
          <button typeCheck="button" className="cancelarbtn" onClick={() => navigate('/')}>
            Cancelar
          </button>
          
          <button type="submit" className="cadastrarbtn">Cadastrar</button>
        </div>
      </div>
    </form>
  );
}