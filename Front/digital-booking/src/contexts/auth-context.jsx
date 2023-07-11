/* eslint-disable react/prop-types */
import jwt_decode from 'jwt-decode'
import { createContext, useState } from "react";
import { toast } from 'react-toastify';

export const AuthContext = createContext({});

const AuthProvider = ({children}) => {


    const [ usuarioNum, setUsuarioNnum ] = useState('');
    const [ usuarioNome, setUsuarioNome ] = useState('');
    const [ usuarioSobrenome, setUsuarioSobrenome ] = useState('');
    const [ usuarioEmail, setUsuarioEmail ] = useState('');
    const [ expiration, setExpiration ] = useState('');
    const [ iniciais, setIniciais ] = useState('');
    const [ funcao, setFuncao ] = useState('');
    const [ tipoCampoSenha, setTipoCampoSenha ] = useState('password');
    const [ iniciarSessao, setIniciarSessao ] = useState(false);
    const [ criarConta, setCriarConta ] = useState(false);
    const [ logado, setLogado ] = useState(localStorage.getItem("@token") !== null);
    const [ exibirSenha, setExibirSenha ] = useState(false);
    const [ filtraPorCidade, setFiltraPorCidade ] = useState('');
    const [ filtraPorCategoria, setFiltraPorCategoria ] = useState('');
    const [ dataCheckInCheckOut, setDataCheckInCheckOut ] = useState([])

    const parseToken = (token) => {
        const jsonPayload = jwt_decode(token);
        setUsuarioNnum( jsonPayload.num );
        setUsuarioNome( jsonPayload.nome );
        setUsuarioSobrenome( jsonPayload.sobrenome );
        setUsuarioEmail( jsonPayload.email );
        setIniciais(jsonPayload.nome.substr(0, 1).toUpperCase()+jsonPayload.sobrenome?.substr(0, 1).toUpperCase())
        setExpiration(jsonPayload.exp);
        setFuncao(jsonPayload.funcao);
        return jsonPayload;
    }

    const saveData = (token) =>{
        localStorage.setItem("@token",token);
        parseToken(token)
    }
    
    const removeData = () =>{
        setIniciarSessao(false);
        setCriarConta(false);
        localStorage.removeItem("@token");
    }
    
    const getToken = () => {
        return parseToken(localStorage.getItem("@token"));
    }
    
    const autenticado = () => {
        setLogado(localStorage.getItem("@token") !== null );
        if(expiration != 0 && (Date.now() >= (expiration*1000))){
            removeData()
            logado ? toast.info("Sua sessão expirou") : console.log("logado",logado)
        }
        return localStorage.getItem("@token") !== null;
    }

    const atualizaTipoCampoSenha = () =>{
        !exibirSenha ? setTipoCampoSenha('text') : setTipoCampoSenha('password');
    }

    return(
        <AuthContext.Provider value={{
            usuarioNum: usuarioNum,
            usuarioNome: usuarioNome,
            usuarioSobrenome: usuarioSobrenome,
            usuarioEmail: usuarioEmail,
            iniciais: iniciais,
            funcao: funcao,
            iniciarSessao: iniciarSessao,
            criarConta: criarConta,
            logado: logado,
            tipoCampoSenha: tipoCampoSenha,
            exibirSenha: exibirSenha,
            filtraPorCidade: filtraPorCidade,
            filtraPorCategoria: filtraPorCategoria,
            dataCheckInCheckOut: dataCheckInCheckOut,
            // setUsuarioNome: setUsuarioNome,
            // setUsuarioSobrenome: setUsuarioSobrenome,
            // setUsuarioEmail: setUsuarioEmail,
            setIniciarSessao: setIniciarSessao,
            setCriarConta: setCriarConta,
            setExibirSenha: setExibirSenha,
            setFiltraPorCidade: setFiltraPorCidade,
            setFiltraPorCategoria: setFiltraPorCategoria,
            setDataCheckInCheckOut: setDataCheckInCheckOut,
            saveData,
            removeData,
            getToken,
            autenticado,
            atualizaTipoCampoSenha,
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;