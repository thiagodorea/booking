import "./styles.css"
import BaseTemplate from "../../templates/base-template";
import api from "../../service/api";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/auth-context";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleExclamation, faEyeSlash, faSpinner } from "@fortawesome/free-solid-svg-icons";
import { validaEmail } from "../../utils/valida-email";
import { toast } from "react-toastify";


const IniciarSessao = () =>{
    
    const dataContext = useContext(AuthContext);
    const navigate = useNavigate();
    const { state } = useLocation() 
    

    const [ email, setEmail ] = useState("");
    const [ senha, setSenha ] = useState("");
    const [ msgError, setMsgError ] = useState("");
    const [ msgValidacao, setMsgValidacao ] = useState([]);
    const [ done, setDone ] = useState(false);
    
    useEffect(()=>{
        dataContext.autenticado();
        dataContext.logado;
        dataContext.setIniciarSessao(true);
        dataContext.setCriarConta(false);
        dataContext.setExibirSenha(false);
        if (dataContext.tipoCampoSenha == 'text') ocultarExibirSenha();
        if ( dataContext.autenticado() )navigate('/')
    },[]);

    const ocultarExibirSenha = () =>{   
        dataContext.setExibirSenha(!dataContext.exibirSenha);
        dataContext.atualizaTipoCampoSenha()
    }

    const camposForm = {
        email: email,
        senha: senha,
    }

    const limparForm = () => {
        setEmail(''),
        setSenha('')
    };

    const validaCampo = (camposForm) =>{
    setMsgValidacao([]);
    let msg = [];
    if(camposForm.email == "" || !validaEmail(camposForm.email))
        msg.push("email");
    if(camposForm.senha == "" || camposForm.senha.length < 6 )
        msg.push("senha");
    if(msg != ""){
        setMsgValidacao(msg);
        return true;
    }
    return false;
    }

    const entrar = async () =>{
        setMsgError('');
        validaCampo(camposForm)
        if(!validaCampo(camposForm)){
            try {
                setDone(true);
                const {data} = await api.post("/auth",{ 
                    email: email,
                    senha: senha
                })
                dataContext.saveData(data.token);
                limparForm();
                console.log(navigate(-1))
                navigate(-1);
            } catch (error) {
                setDone(false);
                toast.error(error.response.data);
            }
        }        
    }
    
    return(
        <BaseTemplate>
        <>
        <div className="h-100 bg-db-backgroud d-flex justify-content-center align-items-center">
            <div className="container d-flex justify-content-center">
                <div className="col-10 col-sm-8 col-lg-5">
            {state ?
            <div className="alert alert-danger d-flex align-items-center" role="alert">
                <FontAwesomeIcon icon={faCircleExclamation} className="pe-2 fs-4"/> {state}
            </div> : null}
                    <div className="mb-4 d-flex justify-content-center">
                        <span className="fs-4 mb-1 text-primary fw-bold">Iniciar sessão</span>
                    </div>
                    <form className="mb-3 row">
                        <div className="mb-3 ">
                            <label htmlFor="email" className="form-label fw-bold">E-mail</label>
                            <input type="email" className= {msgValidacao.indexOf('email') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }  id="email"  onChange={(event) => setEmail(event.target.value)}  />
                            <div className="invalid-feedback">*E-mail inválido </div>
                        </div>
                        <div className="mb-5 position-relative">
                            <label htmlFor="senha" className="form-label fw-bold">Senha</label>
                            <FontAwesomeIcon icon={faEyeSlash} 
                                className= { msgValidacao.indexOf('senha') != -1 ?  "btn btn-link position-absolute top-0 end-0 pt-3 pe-3 mt-4 me-4 fs-5 text-dark-light" : " btn btn-link position-absolute top-0 end-0 pt-3 mt-4 me-1 fs-5 text-dark-light"} 
                                onClick={() => ocultarExibirSenha()}
                            />
                            <input type={dataContext.tipoCampoSenha} className= { msgValidacao.indexOf('senha') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" } id="senha" onChange={(event)=> setSenha(event.target.value)} />
                            <div className="invalid-feedback">*Senha fora do padrão, minímo 6 caracteres  </div>
                        </div>
                    </form>
                        <div className="d-grid col-6 mx-auto mb-3">
                            <button className="btn btn-primary shadow-sm text-white" onClick={()=>entrar()} >  {!done ? 'Entrar' : <FontAwesomeIcon icon={faSpinner} spinPulse /> }</button>
                        </div>
                    <div className=" d-flex justify-content-center">
                        <span className="fs-6">Ainda não tem conta? <Link to={'/criar_conta'}> Registre-se</Link></span>
                    </div>
                    { msgError.length > 0 ?
                        <div className="alert alert-danger d-flex align-items-center" role="alert">
                            <FontAwesomeIcon icon={faCircleExclamation} className="pe-3"/>
                            <div> {msgError} </div>
                        </div>
                    : null }
                    
                </div>
            </div>
        </div>
        </>
        </BaseTemplate>
    )
}
export default IniciarSessao;