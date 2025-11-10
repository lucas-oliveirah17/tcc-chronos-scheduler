import { Routes, Route } from 'react-router-dom';

// Páginas de exemplo
const Home = () => <h1>Página Inicial (Home)</h1>;
const Login = () => <h1>Página de Login</h1>;

export function AppRoutes() {
    return (
        <Routes>
            <Route path="/"  element={<Home />} />
            <Route path="/login" element={<Login />} />
        </Routes>
    );
}