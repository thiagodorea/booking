/* eslint-disable react/prop-types */
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom"
import { useContext } from "react"
import Home from "./pages/home"
import IniciarSessao from "./pages/iniciar-sessao"
import AuthProvider, { AuthContext } from "./contexts/auth-context"
import CriarConta from "./pages/criar-conta"
import Detalhes from "./pages/detalhes"
import NotFound from "./pages/not-found"
import Reserva from "./pages/reserva"
import MinhasReservas from "./pages/minhas-reservas"
import Configuracao from "./pages/configuracoes"



function App() {
  
  const PrivateRoute = (props) => {
    const dataContext = useContext(AuthContext);
    return dataContext.autenticado() ? props.children : <Navigate to="/iniciar_sessao"  state={props.dados =='reserva' ? "Para fazer uma reserva você precisa estar logado." : ""}/>;
  }

  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/iniciar_sessao" element={<IniciarSessao />} />
          <Route path="/criar_conta" element={<CriarConta />} />
          <Route path="/produto/:uidProduto" element={<Detalhes />} />
          <Route path='/produto/:uidProduto/reserva' element={
            <PrivateRoute dados={'reserva'} >
              <Reserva />
            </PrivateRoute>
            }/>
          <Route path='/reservas' element={
            <PrivateRoute dados={'reservas'} >
              <MinhasReservas />
            </PrivateRoute>
            }/>
          <Route path='/configuracao' element={
            <PrivateRoute dados={'configuracao'} >
              <Configuracao />
            </PrivateRoute>
            }/>
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App