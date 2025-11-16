import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { usuarioService } from '../../services/usuarioService';
import { UsuarioForm } from '../../components/UsuarioForm';

export function ClienteEditPage() {
  const [cliente, setCliente] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCliente = async () => {
      try {
        const data = await usuarioService.getUsuarioById(id);
        if (data.perfil !== 'CLIENTE') {
           navigate('/admin/clientes');
        }
        setCliente(data);
      } catch (error) {
        console.error("Erro ao buscar cliente:", error);
        navigate('/admin/clientes');
      }
    };
    fetchCliente();
  }, [id, navigate]);

  const handleUpdate = async (formData) => {
    try {
      await usuarioService.updateUsuario(id, formData);
      navigate('/admin/clientes');
    } catch (error) {
      console.error("Erro ao atualizar cliente:", error);
      alert("Falha ao atualizar cliente.");
    }
  };

  if (!cliente) {
    return <div>Carregando...</div>;
  }

  return (
    <div className="admin-page-container">
      <h2>Editar Cliente: {cliente.nome}</h2>
      <UsuarioForm 
        onSubmit={handleUpdate} 
        dadosIniciais={cliente} 
        isEdit={true} 
      />
    </div>
  );
}