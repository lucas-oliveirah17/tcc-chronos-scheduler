import { useEffect, useState} from "react";

export function Home() {
  const [token, setToken] = useState("");

  useEffect(() => {
    const savedToken = localStorage.getItem("token");
    if (savedToken) {
      setToken(savedToken);
    }
  }, []);

  return (
    <>
      <h1>Página Inicial</h1>
      {token ? (
          <h2>Bem-vindo! Seu token é:</h2>
        ) : (
          <h2>Nenhum token encontrado.</h2>
        )}
        <p>{token}</p>
    </>
  );
}