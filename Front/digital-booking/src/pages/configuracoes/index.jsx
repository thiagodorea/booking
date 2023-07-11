/* eslint-disable react/no-unknown-property */
import  "./styles.css"
import BaseTemplate from "../../templates/base-template";
import api from "../../service/api";
import Select from 'react-select'
import CreatableSelect from 'react-select/creatable';
import { Link, useLocation, useNavigation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft, faMagnifyingGlass, faSpinner } from "@fortawesome/free-solid-svg-icons";
import { useContext, useEffect, useState } from "react";
import { toast } from "react-toastify";
import { AuthContext } from "../../contexts/auth-context";
import MensagemBemSucedida from "../mensagem-bem-sucedida";
import Dropzone from 'react-dropzone';
import FileList from "../../componentes/upload/file-list";
import { uniqueId } from "lodash";
import { filesize } from "filesize";


const Configuracoes = () =>{

    const dataContext = useContext(AuthContext);
    const { state } = useLocation() 
    const navigate = useNavigation();
        
    const [ uid, setUid ] = useState('')
    const [ nome, setNome ] = useState('')
    const [ descricao, setDescricao ] = useState('')
    const [ latitude, setLatitude ] = useState('')
    const [ longitude, setLongitude ] = useState('')
    const [ qualificacao, setQualificacao ] = useState(1)
    const [ classificacao, setClassificacao ] = useState('')
    const [ caracteristicas, setCaracteristicas ] = useState([])
    const [ caracteristicasSelecionada, setCaracteristicasSelecioanda ] = useState([])
    const [ imagens, setImagens ] = useState('')
    const [ imagensLista, setImagensLista ] = useState('')
    const [ categoria, setCategoria ] = useState('')
    const [ cidade, setCidade ] = useState('')
    const [ endereco, setEndereco ] = useState('')
    const [ regras, setRegras ] = useState('')
    const [ saudeSeguranca, setSaudeSeguranca ] = useState('')
    const [ politicaCancelamento, setPoliticaCancelamento ] = useState('')
    const [ listaCategorias, setListaCategorias ] = useState([])
    const [ listaCaracteristicas, setListaCaracteristicas ] = useState([])
    const [ listaCidades, setListaCidades ] = useState([])
    const [ msgValidacao, setMsgValidacao ] = useState([]);
    const [ done, setDone ] = useState(false);
    const [ numProduto, setNumProduto ] = useState('');
    const [ produtoCriado, setProdutoCriado ] = useState(false);
    const [ pesquisaProdutoEdicao, setPesquisaProdutoEdicao ] = useState('');
    const [ pesquisaProdutoEdicaoState, setPesquisaProdutoEdicaoState ] = useState(state);
    const [ isAtualiza, setIsAtualiza ] = useState('');
    const [ imagensUpload, setImagensUpload ] = useState([]);


    useEffect(()=>{
        getCategorias()
        getCidades()
        getCaracterísticas()
        if(pesquisaProdutoEdicaoState){
            setPesquisaProdutoEdicao(pesquisaProdutoEdicaoState)
            pesquisar()
        }
    },[pesquisaProdutoEdicao])

    useEffect(()=>{
        imagensUpload.forEach(img => processarUpload(img))
    },[imagensUpload])

    const renderDragMessage = (isDragAccept, isDragReject) => {
        if(isDragReject){
            return <p className="m-0 text-center p-2 text-danger">Arquivo não suportado</p>
        }
        if(!isDragAccept){
            return <p className="m-0 text-center p-2">Arraste e solte as imagens aqui, ou clique para selecionar</p>
        }
        return <p className="m-0 text-center p-2 text-success" >Solte os arquivos aqui</p>
    }

    const accept= {
        'image/png': ['.png'], 
        'image/jpeg': ['.jpg', '.jpeg'] 
    }


    // upimagem
    const onUpload = (files) => {
        let uploadedFiles = files.map(file =>({
            file,
            id: uniqueId(),
            name: file.name,
            readableSize: filesize(file.size),
            preview: URL.createObjectURL(file),
            progress: 0,
            uploaded: false,
            error: false,
            url: null
        }))
        setImagensUpload( imagensUpload.concat(uploadedFiles))
        imagensUpload.forEach(img => processarUpload(img))
    }

    const processarUpload =  (img) => {
        console.log("pre",img)
        let dadosImagemParaUpLoad = {
            fileName: img.file.name,
            contentType: img.file.type,
            contentLength: img.file.size,
        }
        api.post("/uploads/imagens",dadosImagemParaUpLoad,{
            onUploadProgress: e =>{
                const progress = parseInt(Math.round((e.loaded * 100) / e.total))
                updateFile(img.id, { progress },"post")
            }
        })
        .then((response) => {
            console.log("pre then",response)
            const preurl = response.data.uploadSignedUrl
            const url = preurl.split("?")
            processarEnvioImagem(response,img)
            updateFile(img.id, { 
                uploaded: true,
                id: response.data.fileReferenceId,
                url: url[0],
            },"response")
        })
        .catch((err) => {
            console.log("pre cat",err)
            updateFile(img.id, { error: true },"error")
        })
        
    }

    const processarEnvioImagem = (response,img)=>{
        console.log(response.data.uploadSignedUrl)
        localStorage.setItem("upImagem",true);
        api.put(response.data.uploadSignedUrl,img.file,{'headers':{
            "Content-Type": img.file.type
        }})
        .then((res)=>{
            console.log("proc res",res)
            localStorage.removeItem("upImagem");
        })
        .catch((err) =>{
            console.log("proc err",err)
            updateFile(img.id, { error: true },"error")
        })
    }

    const updateFile = (id, data,origem) => {
        return  setCaracteristicas(
            Array.isArray(imagensUpload) 
            ? imagensUpload.map(imagemUpload => {
                if(id === imagemUpload.id && origem =="post"){imagemUpload.progress = data.progress} 
                if(id === imagemUpload.id && origem =="response"){imagemUpload.id = data.id, imagemUpload.url = data.url, imagemUpload.uploaded = data.uploaded } 
                if(id === imagemUpload.id && origem =="error"){imagemUpload.error = data.error} 
            } ) 
            : []);
    } 

    const novoProduto = {
        tipo: "produto",
        mensagem: "Sua propriedade foi cadastrada com sucesso",
        numProduto:numProduto
    }

    const camposForm = {
        uid: uid,
        nome: nome,
        descricao: descricao,
        endereco: endereco,
        latitude: latitude,
        longitude: longitude,
        qualificacao: qualificacao,
        classificacao: classificacao,
        caracteristicasLista: caracteristicasSelecionada,
        imagens: imagens,
        imagensLista: imagensLista,
        categoria:categoria,
        cidade: cidade,
        regras: regras,
        saudeSeguranca: saudeSeguranca,
        politicaCancelamento: politicaCancelamento
    }

    const carregarCaracteristicas = (list) => {
        setCaracteristicas(Array.isArray(list) ? list.map(x => x.uid) : []);
        setCaracteristicasSelecioanda(Array.isArray(list) ? list.map(x => ({"uid":x.uid})) : [])
    }


    const carregarCamposForm = (dados) => {
        setUid(dados.uid),
        setNome(dados.nome),
        setCategoria(dados.categoria.uid),
        setQualificacao(dados.qualificacao),
        setClassificacao(dados.classificacao)
        setDescricao(dados.descricao),
        setEndereco(dados?.endereco),
        setCidade(dados.cidade.uid),
        setLatitude(dados.latitude),
        setLongitude(dados.longitude),
        carregarCaracteristicas(dados.caracteristicasLista),
        setImagens(dados.imagens),
        setImagensLista(dados.imagensLista),
        setRegras(dados?.regras),
        setSaudeSeguranca(dados?.saudeSeguranca),
        setPoliticaCancelamento(dados?.politicaCancelamento)
    }

    const limparForm = () => {
        setUid(''),
        setNome(''),
        setCategoria(''),
        setQualificacao(1),
        setClassificacao(''),
        setDescricao(''),
        setEndereco(''),
        setCidade(''),
        setLatitude(''),
        setLongitude(''),
        carregarCaracteristicas([]),
        setImagens([]),
        setImagensLista([]),
        setRegras(''),
        setSaudeSeguranca(''),
        setPoliticaCancelamento('')
    };

    const validaCampo = (camposForm) =>{
    setMsgValidacao([]);
    let msg = [];
    if(camposForm.nome == "" || camposForm.nome.length < 2)
        msg.push("NOME");
    if(camposForm.categoria == null || camposForm.categoria =='' )
        msg.push("CATEGORIA");
    if(camposForm.endereco == "" || camposForm.endereco > 0 )
        msg.push("ENDERECO");
    if(camposForm.qualificacao == ""  )
        msg.push("QUALIFICAÇÃO");
    if(camposForm.cidade == null || camposForm.cidade =='')
        msg.push("CIDADE");
    if(camposForm.latitude == ""  )
        msg.push("LATITUDE");
    if(camposForm.longitude == "")
        msg.push("LONGITUDE");
    if(camposForm.descricao == "" || camposForm.descricao.length > 512 )
        msg.push("DESCRICAO");
    if(camposForm.caracteristicasLista.length == 0 )
        msg.push("CARACTERISTICAS");
    if(camposForm.regras == "" )
        msg.push("REGRAS");
    if(camposForm.saudeSeguranca == "" )
        msg.push("SAUDE E SEGURANCA");
    if(camposForm.politicaCancelamento == "" )
        msg.push("POLITICA DE CANCELAMENTO");
    if(msg != ""){
        setMsgValidacao(msg);
        msg.forEach((campo) =>{
            toast.warning("O Campo "+campo+ " é de prenchimento obrigatório.");
        })
        return true;
    }
    return false;
    }


    const pesquisar = async () =>{
        if(!dataContext.autenticado()){ return}
        // limparForm();
        if(pesquisaProdutoEdicao){
            setDone(true);
            try {
                const {data} = await api.get(`/produtos/${pesquisaProdutoEdicao}`)
                carregarCamposForm(data)
                setDone(false);
                setIsAtualiza(true);
                setPesquisaProdutoEdicaoState('')
            } catch (error) {
                setDone(false);
                setIsAtualiza(false);
                toast.error(error.response.data)
            }
        }
        
    }

    const atualizar = async () => {
        if(!dataContext.autenticado()){ return}
        let produtoAtualizar = {
            uid: uid,
            nome: nome,
            descricao: descricao,
            endereco: endereco,
            latitude: latitude,
            longitude: longitude,
            qualificacao: qualificacao,
            classificacao: 9,
            caracteristicasLista: caracteristicasSelecionada,
            imagens: imagensUpload,
            imagensLista: imagensLista,
            categoria: {"uid":categoria},
            cidade: {"uid":cidade},
            regras: regras,
            saudeSeguranca: saudeSeguranca,
            politicaCancelamento: politicaCancelamento
        }
        if(!validaCampo(camposForm)){
            console.log(JSON.stringify(produtoAtualizar ,null,4))
            try {
                setDone(true);
                const {data} = await api.patch("/produtos", produtoAtualizar);
                setDone(false);
                // limparForm();
                console.log(navigate(-1))
                navigate(-1);
                console.log(data)
            } catch (error) {   
                console.log(error)
                setDone(false);
                // toast.error("Não foi possível atualizar o produto. Por favor, tente novamente mais tarde");
                toast.warn(error.response)
            }
        }
    };

    const criar = async () => {
        if(!dataContext.autenticado()){ return}
        console.log("passei")
        let produtoCriar = {
            nome: nome,
            descricao: descricao,
            endereco: endereco,
            latitude: latitude,
            longitude: longitude,
            qualificacao: qualificacao,
            classificacao: 9,
            caracteristicasLista: caracteristicasSelecionada,
            imagens: imagensUpload,
            categoria: {"uid":categoria},
            cidade: {"uid":cidade},
            regras: regras,
            saudeSeguranca: saudeSeguranca,
            politicaCancelamento: politicaCancelamento
        }
        if(!validaCampo(camposForm)){
            console.log(JSON.stringify({produtoCriar } ,null,4))

            try {
                setDone(true);
                const {data} = await api.post("/produtos", produtoCriar);
                setNumProduto(data.uid);
                setDone(false);
                setProdutoCriado(true);
                console.log(data)
                // limparForm();
            } catch (error) {
                setDone(false);
                console.log(error.response.data)
                toast.error("Não foi possível criar o produto. Por favor, tente novamente mais tarde");
            }
        }
    };

    const getCategorias = async () => {
        try {
            const response = await api.get("/categorias")
            setListaCategorias(response.data)
        } catch (error) {
            toast.error('Erro ao buscar Categorias.')
        }
    }

    const getCaracterísticas = async () => {
        try {
            const response = await api.get("/caracteristicas")
            setListaCaracteristicas(response.data)
        } catch (error) {
            toast.error('Erro ao buscar Características.')
        }
    }

    const getCidades = async () => {
        try {
            const response = await api.get("/cidades")
            setListaCidades(response.data);
            } catch (error) {
            toast.error('Erro ao carregar as cidade.')
        }
    }

    const cidades = listaCidades.map((cidade) =>(
        {value:cidade.uid, label:cidade.nome}
    ))

    const categorias = listaCategorias.map((categoria) =>(
        {value: categoria.uid, label:categoria.descricao}
    ))

    const caracteristicasList = listaCaracteristicas.map((caracteristica) =>(
        {value: caracteristica.uid, label:caracteristica.nome}
    ))

    const handleChangeCaracteristicas = (e) => {
        setCaracteristicas(Array.isArray(e) ? e.map(x => x.value) : []);
        setCaracteristicasSelecioanda(Array.isArray(e) ? e.map(x => ({"uid":x.value})) : [])
    }

    const handleCreateCidade = (inputValue) => {
        // setIsLoading(true);
        console.group('Option created');
        console.log('Wait a moment...');
        console.log(inputValue);
        // const newOption = createOption(inputValue);
        // console.log(newOption);
        // console.groupEnd();
        // setIsLoading(false);
        // setOptions([...options, newOption]);
        // setValue(newOption.id);
    };

    const theme = theme => ({
        ...theme,
        colors: {
            ...theme.colors,
            primary50: "#F0572D",
            primary25: "#FFBDAD",
            primary: "#F0572D"
        }
    });

    return(
        <BaseTemplate>
        { !produtoCriado ?
        <>
        <div className="container-fluid px-0">
            <div className='py-2 bg-secondary text-white d-flex justify-content-center'>
                <div className="col-11 d-flex align-items-center">
                    <div className='col'>
                        <h1 className='fs-4 m-0'> Configuração </h1>
                    </div>
                    <div className='col-auto text-end'>
                        <Link to={-1}> <FontAwesomeIcon icon={faAngleLeft} className='fs-2 text-white' /> </Link>
                    </div>
                </div>
            </div>
        </div>

        <div className="container-fluid bg-light">
                <div className="row d-flex justify-content-center">
                    <div className="col-11">
                        <h2 className="fs-5 fw-bold text-secondary mt-3  d-flex justify-content-end">
                        <div className="col-lg-4">
                            <form className="input-group rounded shadow">
                                <input type="text" className="form-control" placeholder="Para editar Informe o produto" onChange={(event) => setPesquisaProdutoEdicao(event.target.value)} />
                                <button className="btn btn-outline-primary" type="button" id="button-addon2" onClick={pesquisar} > {!done ? <FontAwesomeIcon icon={faMagnifyingGlass} /> : <FontAwesomeIcon icon={faSpinner} spinPulse />} </button>
                            </form>
                        </div>
                            </h2>
                        <h2 className="fs-5 fw-bold text-secondary mt-3">Cadastrar Propriedade</h2>
                        <div className="row row-gap-3 py-3 bg-white shadow rounded">
                            <div className="col-lg-6">
                                <label htmlFor="nome" className="form-label fw-bold">Nome<span className="text-danger">*</span></label>
                                <input type="text" id="nome" value={nome}  className= {msgValidacao.indexOf('NOME') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setNome(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            <div className="col-lg-4">
                                <label htmlFor="categoria" className="form-label fw-bold">Categoria<span className="text-danger">*</span></label>
                                <Select options={categorias} placeholder= "Escolha na lista" id="categoria" className={msgValidacao.indexOf('CATEGORIA') != -1 ? "form-control p-0 shadow-sm is-invalid" : "form-control p-0 shadow-sm" }
                                    value={categorias.filter((option) => {
                                        return option.value === categoria;
                                    })}
                                    theme={theme}
                                    isClearable={true}
                                    onChange={(newValue) =>{ setCategoria(newValue?.value)}}
                                />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            <div className="col-lg-2">
                                <label htmlFor="qualificacao" className="form-label fw-bold">Qualificação<span className="text-danger">*</span></label>
                                <input type="number" value={qualificacao} min={1} max={5} id="qualificacao"  className= {msgValidacao.indexOf('QUALIFICAÇÃO') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setQualificacao(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            <div className="col-lg-12">
                                <label htmlFor="descricao" className="form-label fw-bold">Descrição<span className="text-danger">*</span></label>
                                <textarea  rows="3" type="descricao" id="descricao" value={descricao} className= {msgValidacao.indexOf('DESCRICAO') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setDescricao(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                                <span className={descricao.length > 512 ? "fs-6 text-danger": "fs-6"} > Tamanho 512 / {512 - descricao.length}</span>
                            </div>
                            <div className="col-lg-6">
                                <label htmlFor="endereco" className="form-label fw-bold">Endereço<span className="text-danger">*</span></label>
                                <input type="text" id="endereco" value={endereco}  className= {msgValidacao.indexOf('ENDERECO') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setEndereco(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            
                            <div className="col-lg-6">
                                <label htmlFor="cidade" className="form-label fw-bold">Cidade<span className="text-danger">*</span></label>
                                <CreatableSelect options={cidades} placeholder= "Informe a cidade ou escolha na lista" id="cidade" className={msgValidacao.indexOf('CIDADE') != -1 ? "form-control p-0 shadow-sm is-invalid" : "form-control p-0 shadow-sm" }
                                    value={cidades.filter((option) => {
                                        return option.value === cidade;
                                    })}
                                    theme={theme}
                                    isClearable={true}
                                    onChange={(newValue) =>{ setCidade(newValue?.value)}}
                                    onCreateOption={handleCreateCidade}
                                />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            <div className="col-lg-6">
                                <label htmlFor="latitude" className="form-label fw-bold">Latitude<span className="text-danger">*</span></label>
                                <input type="text" id="latitude" value={latitude}  className= {msgValidacao.indexOf('LATITUDE') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setLatitude(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            <div className="col-lg-6">
                                <label htmlFor="longitude" className="form-label fw-bold">Longitude<span className="text-danger">*</span></label>
                                <input type="text" id="longitude" value={longitude} className= {msgValidacao.indexOf('LONGITUDE') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setLongitude(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                        </div>

                        <h2 className="fs-5 fw-bold text-secondary mt-3">Adicione Características </h2>
                        <div className="row row-gap-3 py-3 bg-white shadow rounded">
                            <div className="col-lg-12">
                                <label htmlFor="características" className="form-label fw-bold">Nome<span className="text-danger">*</span></label>
                                <Select options={caracteristicasList} placeholder= "Escolha as características na lista" id="características" className={msgValidacao.indexOf('CARACTERISTICAS') != -1 ? "form-control p-0 shadow-sm is-invalid" : "form-control p-0 shadow-sm" }
                                    value={caracteristicasList.filter(obj => caracteristicas.includes(obj.value))}
                                    theme={theme}
                                    isClearable={true}
                                    closeMenuOnSelect={false}
                                    isMulti
                                    onChange={handleChangeCaracteristicas}
                                />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                        </div>

                        <h2 className="fs-5 fw-bold text-secondary mt-3">Adicione Imagens </h2>
                        <div className="row row-gap-3 py-3 bg-white shadow rounded">
                            <div className="col-lg-12">
                                <label htmlFor="imagem" className="form-label fw-bold">Imagem<span className="text-danger">*</span></label>
                                <div className="form-control p-2 shadow-sm rounded">
                                    <Dropzone accept={accept}  onDropAccepted={onUpload}>
                                        { ({ getRootProps, getInputProps, isDragAccept, isDragReject}) => (
                                            <section>
                                                <div {...getRootProps()}  className={isDragAccept ? "dropzone border-success rounded" : isDragReject ? "dropzone border-danger rounded" : "dropzone rounded" }>
                                                    <input {...getInputProps()} type="file"/>
                                                    {renderDragMessage(isDragAccept, isDragReject)}
                                                </div>
                                                { !!imagensUpload.length &&(
                                                    <FileList files={imagensUpload} />
                                                ) }
                                            </section>
                                        ) }
                                    </Dropzone>
                                </div>
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                        </div>

                        <h2 className="fs-5 fw-bold text-secondary mt-3">Politicas </h2>
                        <div className="row row-gap-3 py-3 mb-4 bg-white shadow rounded">
                            <div className="col-lg-4">
                                <h3 className="fs-5">Regras<span className="text-danger">*</span></h3>
                                <label htmlFor="regras" className="form-label fw-bold">Descrição</label>
                                <textarea  rows="3" type="regras" id="regras" value={regras} className= {msgValidacao.indexOf('REGRAS') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setRegras(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            <div className="col-lg-4">
                                <h3 className="fs-5">Saúde e segurança<span className="text-danger">*</span></h3>
                                <label htmlFor="saude-seguranca" className="form-label fw-bold">Descrição</label>
                                <textarea  rows="3" type="saude-seguranca" id="saude-seguranca" value={saudeSeguranca} className= {msgValidacao.indexOf('SAUDE E SEGURANCA') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setSaudeSeguranca(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                            <div className="col-lg-4">
                                <h3 className="fs-5">Política de cancelamento<span className="text-danger">*</span></h3>
                                <label htmlFor="politica" className="form-label fw-bold">Descrição</label>
                                <textarea  rows="3" type="politica" id="politica" value={politicaCancelamento} className= {msgValidacao.indexOf('POLITICA DE CANCELAMENTO') != -1 ? "form-control shadow-sm is-invalid" : "form-control shadow-sm" }   onChange={(event) => setPoliticaCancelamento(event.target.value)} />
                                <div className="invalid-feedback">*Campo Obrigatório </div>
                            </div>
                        </div>
                        <div className="d-grid col-2 mx-auto">
                            <button className="btn btn-primary mb-5  text-white " onClick={isAtualiza? () => atualizar(): () => criar()} > {!done ?  isAtualiza ? 'Atualizar' : 'Cadastrar'   : <FontAwesomeIcon icon={faSpinner} spinPulse /> } </button>
                        </div>
                    </div>
                </div>
        </div>
        </>
        : <MensagemBemSucedida dados={novoProduto}/>
        }
        </BaseTemplate>
    )
    
}

export default Configuracoes;