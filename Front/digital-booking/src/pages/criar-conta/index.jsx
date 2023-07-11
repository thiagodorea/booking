import "./styles.css";
import BaseTemplate from "../../templates/base-template";
import api from "../../service/api";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/auth-context";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEyeSlash, faSpinner } from "@fortawesome/free-solid-svg-icons";
import { validaEmail } from "../../utils/valida-email";
import { toast } from "react-toastify";

const CriarConta = () => {
  
  const dataContext = useContext(AuthContext);
  const navigate = useNavigate();
  
  const [ nome, setNome ] = useState('');
  const [ sobreNome, setSobreNome ] = useState('');
  const [ email, setEmail ] = useState("");
  const [ senha, setSenha ] = useState("");
  const [ repetirSenha, setRepetirSenha] = useState("");
  const [ msgValidacao, setMsgValidacao ] = useState([]);
  const [ done, setDone ] = useState(false);

  useEffect(() => {
    dataContext.autenticado();
    dataContext.logado;
    dataContext.setCriarConta(true);
    dataContext.setIniciarSessao(false);
    dataContext.setExibirSenha(false);
    if (dataContext.tipoCampoSenha == 'text') ocultarExibirSenha();
    if ( dataContext.autenticado())navigate('/')
  }, []);

  const ocultarExibirSenha = () =>{        
    dataContext.setExibirSenha(!dataContext.exibirSenha);
    dataContext.atualizaTipoCampoSenha()
  }

  const camposForm = {
    nome: nome,
    sobrenome: sobreNome,
    email: email,
    senha: senha,
    repetirSenha: repetirSenha,
  }

  const limparForm = () => {
    setNome(''),
    setSobreNome(''),
    setEmail(''),
    setSenha(''),
    setRepetirSenha('')
  };

const validaCampo = (camposForm) =>{
  setMsgValidacao([]);
  let msg = [];
  if(camposForm.nome == "" || camposForm.nome.length < 3)
      msg.push("nome");
  if(camposForm.nome == "" || camposForm.sobrenome.length < 3)
      msg.push("sobreNome");
  if(camposForm.email == "" || !validaEmail(camposForm.email))
      msg.push("email");
  if(camposForm.senha == "" || camposForm.senha.length < 6 )
      msg.push("senha");
  if(camposForm.repetirSenha !== senha || camposForm.repetirSenha.length < 6)
      msg.push("repetirSenha");
  if(msg != ""){
      setMsgValidacao(msg);
      return true;
  }
  return false;
  }

  const criar = async () => {
    validaCampo(camposForm);
    if(!validaCampo(camposForm)){
      try {
        setDone(true);
        const {data} = await api.post("/usuarios",{ 
          nome: nome,
          sobrenome: sobreNome,
          email: email,
          senha: senha,
          perfis: []
        })
        dataContext.saveData(data.token);
        navigate('/');
        limparForm();
      } catch (error) {
        setDone(false);
        toast.error(error.response.data);
      }
    }
  };

  return (
    <BaseTemplate>
      <>
      <div className="h-100 bg-db-backgroud d-flex justify-content-center align-items-center">
        <div className="container d-flex justify-content-center">
          <div className="col-10 col-sm-8 col-lg-6">
            <div className="mb-2 d-flex justify-content-center">
              <span className="fs-4 mb-1 text-primary fw-bold"> Criar conta </span>
            </div>
            <form className="mb-1">
              <div className="row">
                <div className="col-12 col-sm-12 col-lg-6 mb-2">
                  <label htmlFor="nome" className="form-label fw-bold"> Nome </label>
                  <input type="text" className= {msgValidacao.indexOf('nome') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" } id="nome" onChange={(event) => setNome(event.target.value)} />
                  <div className="invalid-feedback">*Este campo é obrigatório </div>
                </div>
                <div className="col-12 col-sm-12 col-lg-6 mb-2">
                  <label htmlFor="sobrenome" className="form-label fw-bold"> Sobrenome </label>
                  <input type="text" className= {msgValidacao.indexOf('sobreNome') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" } id="sobrenome"  onChange={(event) => setSobreNome(event.target.value)} />
                  <div className="invalid-feedback">*Este campo é obrigatório </div>
                </div>
              </div>
              <div className="col mb-2">
                <label htmlFor="email" className="form-label fw-bold"> E-mail </label>
                <input type="email" className= {msgValidacao.indexOf('email') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" } id="email" onChange={(event) => setEmail(event.target.value)} />
                  <div className="invalid-feedback">*E-mail inválido </div>
              </div>
              <div className="col mb-2 position-relative">
                <label htmlFor="senha" className="form-label fw-bold"> Senha </label>
                <FontAwesomeIcon icon={faEyeSlash} 
                    className= { msgValidacao.indexOf('senha') != -1 ?  "btn btn-link position-absolute top-0 end-0 pt-3 pe-3 mt-4 me-4 fs-5 text-dark-light" : " btn btn-link position-absolute top-0 end-0 pt-3 mt-4 me-1 fs-5 text-dark-light"} 
                    onClick={() => ocultarExibirSenha()}
                />
                <input type={dataContext.tipoCampoSenha} className= {msgValidacao.indexOf('senha') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" } id="senha" onChange={(event)=> setSenha(event.target.value)} />
                <div className="invalid-feedback">*Senha fora do padrão, minímo 6 caracteres  </div>
              </div>
              <div className="col mb-4 position-relative">
                <label htmlFor="confirmar-senha" className="form-label fw-bold" > Confirmar Senha </label>
                <FontAwesomeIcon icon={faEyeSlash} 
                    className= { msgValidacao.indexOf('repetirSenha') != -1 ?  "btn btn-link position-absolute top-0 end-0 pt-3 pe-3 mt-4 me-4 fs-5 text-dark-light" : " btn btn-link position-absolute top-0 end-0 pt-3 mt-4 me-1 fs-5 text-dark-light"} 
                    onClick={() => ocultarExibirSenha()}
                />
                <input type={dataContext.tipoCampoSenha} className= {msgValidacao.indexOf('repetirSenha') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" } id="repetirSenha" onChange={(event)=> setRepetirSenha(event.target.value)} />
                <div className="invalid-feedback">*Senhas não conferem</div>
              </div>
            </form>
              <div className="d-grid col-6 mx-auto mb-3">
                <button className="btn btn-primary shadow-sm text-white" onClick={() => criar()} > {!done ? 'Criar conta' : <FontAwesomeIcon icon={faSpinner} spinPulse /> } </button>
              </div>
            <div className=" d-flex justify-content-center">
              <span className="fs-6"> Já tem uma conta? <Link to={"/iniciar_sessao"}> Iniciar Sessão</Link> </span>
            </div>
          </div>
        </div>
      </div>
      </>
    </BaseTemplate>
  );
};
export default CriarConta;

