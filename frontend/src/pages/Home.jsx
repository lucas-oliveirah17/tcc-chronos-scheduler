import { useEffect, useState} from "react";

export function Home() {
  const [usuario, setUsuario] = useState(null);

  useEffect(() => {
    const savedUsuarioString = localStorage.getItem("usuario");

    if (savedUsuarioString) {
      try {
        const usuarioObj = JSON.parse(savedUsuarioString);
        setUsuario(usuarioObj);
      
      } catch(error) {
        console.error("Erro ao ler dados do usuário do armazenamento local: ", error);
        localStorage.removeItem("usuario");
        localStorage.removeItem("token");
      }
    }
  }, []);

  return (
    <div>
      <h1>Página Inicial</h1>
      {usuario ? (
        <div>
          <h2>Bem-vindo de volta, {usuario.perfil} {usuario.nome}!</h2>
        </div>
      ) : (
        <div>
          <h2>Você não está autenticado.</h2>
          <p>Por favor, faça login para acessar o sistema.</p>
        </div>
      )}
    </div>
  );
}