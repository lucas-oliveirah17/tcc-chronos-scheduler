import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { UsuarioForm } from '../../components/UsuarioForm';

export function UsuarioEditPage() {
  const [usuario, setUsuario] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsuario = async () => {
      try {
        const data = await usuarioService.getUsuarioById(id);
        setUsuario(data);
      } catch (error) {
        console.error("Erro ao buscar usuário:", error);
        navigate('/admin/usuarios');
      }
    };
    fetchUsuario();
  }, [id, navigate]);

  const handleUpdate = async (formData) => {
    try {
      await usuarioService.updateUsuario(id, formData);
      navigate('/admin/usuarios');
    } catch (error) {
      console.error("Erro ao atualizar usuário:", error);
      alert("Falha ao atualizar usuário.");
    }
  };

  if (!usuario) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Editar Usuário: {usuario.nome}</h2>
      <UsuarioForm 
        onSubmit={handleUpdate} 
        dadosIniciais={usuario} 
        isEdit={true} 
      />
    </div>
  );
}