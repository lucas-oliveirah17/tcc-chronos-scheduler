import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from '../services/api';
import './Login.css';

export function Login() {
  const navigate = useNavigate();
  
  // State para guardar os dados do formulário
  const [formData, setFormData] = useState({
    email: '',
    senha: '',
  });

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

    try {
      const dadosParaEnviar = {
        email: formData.email,
        senha: formData.senha
      };

      const response = await api.post('/api/auth/login', dadosParaEnviar);
      const data = response.data;

      // Armazena o token JWT no localStorage
      localStorage.setItem("token", data.token);

      // Dispara evento global para atualizar Navbar
      window.dispatchEvent(new Event("authChange"));

      alert("Sucesso no Login!");
      console.log("JWT Token:", data.token);

      // Redireciona o usuário para o home
      navigate("/");

    } catch (error) {
      console.error("Error ao logar:", error);
      if(error.response && error.response.data) {
        alert(`Erro: ${error.response.data}`)  
      }
      alert("Ocorreu um erro ao tentar logar.");
    }
  };

  return (
    <>
      <div className="container">
        <form onSubmit={handleSubmit}>
          <div className="row">
            <h2 style={{ textAlign: "center" }}>
              Logue com suas Redes Sociais ou Manualmente
            </h2>

            <div className="vl">
              <span className="vl-innertext">or</span>
            </div>

            <div className="col">
              <a href="#" className="fb btn">
                <i className="fa fa-facebook fa-fw"></i> Login com Facebook
              </a>
              <a href="#" className="twitter btn">
                <i className="fa fa-twitter fa-fw"></i> Login com Twitter
              </a>
              <a href="#" className="google btn">
                <i className="fa fa-google fa-fw"></i> Login com Google+
              </a>
            </div>

            <div className="col">
              <div className="hide-md-lg">
                <p>Ou cadastre-se manualmente:</p>
              </div>

              <input
                type="email" 
                placeholder="Digite o seu e-mail" 
                name="email" 
                id="email" 
                value={formData.email} 
                onChange={handleChange} 
                required
              />
              <input
                type="password" 
                placeholder="Digite a sua senha" 
                name="senha" 
                id="senha" 
                value={formData.senha} 
                onChange={handleChange} 
                requireda
              />
              <button type="submit" className="loginbtn">Login</button>
            </div>
          </div>
        </form>
      </div>

      <div className="bottom-container">
        <div className="row">
          <div className="col">
            <a href="#" style={{ color: "white" }} className="btn" onClick={() => navigate('/cadastro')}>
              Cadastrar
            </a>
          </div>
          <div className="col">
            <a href="#" style={{ color: "white" }} className="btn">
              Esqueceu a senha?
            </a>
          </div>
        </div>
      </div>
    </>
  );
}