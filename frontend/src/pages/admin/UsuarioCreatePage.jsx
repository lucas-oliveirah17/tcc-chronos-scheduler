import React from 'react';
import { useNavigate } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { UsuarioForm } from '../../components/UsuarioForm';

export function UsuarioCreatePage() {
  const navigate = useNavigate();

  const handleCreate = async (formData) => {
    try {
      await usuarioService.createUsuario(formData);
      navigate('/admin/usuarios'); 
    } catch (error) {
      console.error("Erro ao criar usuário:", error);
      alert("Falha ao criar usuário.");
    }
  };

  return (
    <div className="admin-page-container">
      <h2>Cadastrar Novo Usuário</h2>
      <UsuarioForm onSubmit={handleCreate} isEdit={false} />
    </div>
  );
}