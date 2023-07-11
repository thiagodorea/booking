import "./styles.css"
import DetalhesHeader from "../../componentes/detalhes/detalhes-header";
import BaseTemplate from "../../templates/base-template";
import api from "../../service/api";
import Loading from "../../componentes/Loading";
import Select from 'react-select'
import CreatableSelect from 'react-select/creatable';
import Star from "../../componentes/star/star";
import DetalhesPoliticas from "../../componentes/detalhes/detalhes-politica";
import MensagemBemSucedida from "../mensagem-bem-sucedida";
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Calendar } from 'react-calendar'
import { toast } from "react-toastify";
import { useContext, useEffect, useState } from 'react';
import { useLocation, useParams } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleCheck, faLocationDot, faSpinner } from "@fortawesome/free-solid-svg-icons";
import { AuthContext } from "../../contexts/auth-context";

const Reserva = () => {

    const dataContext = useContext(AuthContext);
    
    const { uidProduto } = useParams();
    const { state } = useLocation();

    const [ produto, setProduto ] = useState();
    const [ date, setDate ] = useState();   
    const [ horaInicioReserva, setHoraInicioReserva ] = useState(''); 
    const [ dataInicioReserva, setDataInicioReserva ] = useState(''); 
    const [ dataFinalReserva, setDataFinalReserva ] = useState(''); 
    const [ uid, setUid ] = useState(''); 
    const [ email, setEmail ] = useState(''); 
    const [ cidade, setCidade ] = useState(''); 
    const [ msgValidacao, setMsgValidacao ] = useState([]);
    const [ temProduto, setTemProduto ] = useState(false);
    const [ done, setDone ] = useState(false);
    const [ reservaBemSucedida, setReservaBemSucedida ] = useState(false);
    const [ numReserva, setNumReserva ] = useState('');
    const [ listaCidades, setListaCidades ] = useState([])

    const getCidades = async () => {
        try {
            const response = await api.get("/cidades")
            setListaCidades(response.data);
        } catch (error) {
            toast.error('Erro ao carregar as cidade.')
        }
    }

    const cidades = listaCidades.map((cidade) =>(
            {value: cidade.uid, label:cidade.nome}
        ))

    const theme = theme => ({
        ...theme,
        colors: {
            ...theme.colors,
            primary50: "#F0572D",
            primary25: "#FFBDAD",
            primary: "#F0572D"
        }
    });

    const horaDeChegada = ([
        {value: "00:00", label:"00:00"},
        {value: "01:00", label:"01:00"},
        {value: "02:00", label:"02:00"},
        {value: "03:00", label:"03:00"},
        {value: "04:00", label:"04:00"},
        {value: "05:00", label:"05:00"},
        {value: "06:00", label:"06:00"},
        {value: "07:00", label:"07:00"},
        {value: "08:00", label:"08:00"},
        {value: "09:00", label:"09:00"},
        {value: "10:00", label:"10:00"},
        {value: "11:00", label:"11:00"},
        {value: "12:00", label:"12:00"},
        {value: "13:00", label:"13:00"},
        {value: "14:00", label:"14:00"},
        {value: "15:00", label:"15:00"},
        {value: "16:00", label:"16:00"},
        {value: "17:00", label:"17:00"},
        {value: "18:00", label:"18:00"},
        {value: "19:00", label:"19:00"},
        {value: "20:00", label:"20:00"},
        {value: "21:00", label:"21:00"},
        {value: "22:00", label:"22:00"},
        {value: "23:00", label:"23:00"}
        ])

    const camposForm = {
        horaInicioReserva: horaInicioReserva,
        dataInicioReserva: dataInicioReserva,
        dataFinalReserva: dataFinalReserva,
        produto: {
            uid: uid
        },
        cliente: {
            email: email,
            cidade: cidade
        }
    }

    const novaReserva = {
        tipo: "reserva",
        mensagem: "Sua reserva foi feita com sucesso.",
        numProduto:numReserva
    }

    const carregarForm = () => {
        setDataInicioReserva( format(date[0],'yyyy-MM-dd')  ),
        setDataFinalReserva( format(date[1],'yyyy-MM-dd') ),
        setEmail(dataContext.usuarioEmail),
        setUid(uidProduto)
    };

    const limparForm = () => {
        setDataInicioReserva(''),
        setDataFinalReserva(''),
        setEmail(''),
        setUid(''),
        setCidade('')
    };

const validaCampo = (camposForm) =>{
    setMsgValidacao([]);
    let msg = [];
    if(camposForm.horaInicioReserva == "")
        msg.push("HORA DE CHEGADA");
    if(camposForm.cliente.cidade == "")
        msg.push("CIDADE");
    if(msg != ""){
        setMsgValidacao(msg);
        msg.forEach((campo) =>{
            toast.warning("O Campo "+campo+ " é de prenchimento obrigatório.");
        })
        return true;
    }
    return false;
    }

    const confirmarReserva = async () => {
        console.log(camposForm);
        dataContext.autenticado();
        carregarForm();
        if(!validaCampo(camposForm)){
            try {
                setDone(true);
                const {data} = await api.post("/reservas",{ 
                    horaInicioReserva: horaInicioReserva,
                    dataInicioReserva: dataInicioReserva,
                    dataFinalReserva: dataFinalReserva,
                    produto: {
                        uid: uid
                    },
                    cliente: {
                        email: email
                    }
                })
                limparForm();
                setNumReserva(data.uid);
                setTimeout(
                    () => (setReservaBemSucedida(true),setDone(false)),2000
                )
                
            } catch (error) {
                setReservaBemSucedida(false)
                setDone(false);
                toast.error("Infelizmente, a reserva não pôde ser completada. Por favor, tente novamente mais tarde");
            }
        }
    }


    const getProduto = async () => {
        try {
            const response = await api.get(`/produtos/${uidProduto}`)
            setProduto(response.data);
            setTemProduto(true)
        } catch (error) {
            toast.error('Erro ao buscar o produto.')
        }
    }

    useEffect(() => {
        getProduto(); 
        getCidades();
        setDate([new Date(state[0]),new Date (state[1])]);
    },[])
    

    return (
        <BaseTemplate>
        { !reservaBemSucedida ?
            temProduto ? 
            <div className="container-fluid bg-light">
                <DetalhesHeader dados={produto} />
                <div className="row gap-1 justify-content-evenly py-3">
                    <div className="col-md-11 col-lg-7">
                        
                        <h3 className="fs-5 fw-bold">Complete seu dados</h3>
                        <div className="rounded shadow bg-white p-3 mb-5">
                            <div className="row g-3">
                                <div className="col-sm-11 col-md-6">
                                    <label htmlFor="nome" className="form-label">Nome</label>
                                    <input type="text" id="nome" className="form-control" disabled value={dataContext.usuarioNome} />
                                </div>
                                <div className="col-sm-11 col-md-6">
                                    <label htmlFor="sobrenome" className="form-label">Sobrenome</label>
                                    <input type="text" id="sobrenome" className="form-control" disabled value={dataContext.usuarioSobrenome} />
                                </div>
                                <div className="col-sm-11 col-md-6">
                                    <label htmlFor="email" className="form-label">Email</label>
                                    <input type="email" id="email" className="form-control" disabled value={dataContext.usuarioEmail} x />
                                </div>
                                <div className="col-sm-11 col-md-6">
                                    <label htmlFor="cidade" className="form-label">Cidade<span className="text-danger fw-bold">*</span></label>
                                    <CreatableSelect  options={cidades} placeholder= "Informe a cidade ou escolha na lista" id="cidade" 
                                        className={msgValidacao.indexOf('CIDADE') != -1 ? "form-control p-0 shadow-sm is-invalid" : "form-control p-0 shadow-sm" }
                                        theme={theme}
                                        isClearable={true}
                                        onChange={(newValue) =>{ setCidade(newValue?.value)}}
                                    />
                                    <div className="invalid-feedback">*Este campo é obrigatório </div>
                                </div>
                            </div>
                        </div>
                        
                        <h3 className="fs-5 fw-bold">Selecione sua data de reserva</h3>
                        <div className="rounded shadow  bg-white p-3 mb-5 ">
                            <Calendar onChange={setDate} value={date} selectRange={true} calendarType={'Hebrew'} 
                            className="d-none d-sm-block calendario"
                            minDate={new Date()}
                            showDoubleView
                            showNeighboringMonth={false}
                            showFixedNumberOfWeeks={false}
                            // tileDisabled={({date}) => {if(disableDates != null )date.getDate() === disableDates.getDate()}}
                            />
                            <Calendar onChange={setDate} value={date} selectRange={true} calendarType={'Hebrew'} 
                            className="d-block d-sm-none calendario"
                            minDate={new Date()}
                            showNeighboringMonth={false}
                            showFixedNumberOfWeeks={false}
                            />
                        </div>

                        <h3 className="fs-5 fw-bold">Seu horário de chegada</h3>
                        <div className="rounded shadow bg-white  p-3 mb-5 ">
                            <p className="fw-bold"><FontAwesomeIcon icon={faCircleCheck} className="pe-2 fs-5" />Seu quarto estará pronto para check-in entre 10:00 e 23:00</p>
                            <p>Indique a sua hora prevista de chegada<span className="text-danger fw-bold">*</span> </p>
                            <Select options={horaDeChegada} placeholder= "Selecione sua hora de chegada" id="horaDeChegada" className={msgValidacao.indexOf('HORA DE CHEGADA') != -1 ? "form-control p-0 shadow-sm is-invalid" : "form-control p-0 shadow-sm" }
                                theme={theme}
                                isClearable={true}
                                onChange={(horaSelecionada) =>{ setHoraInicioReserva(format(date[0],'yyyy-MM-dd')+"T"+ horaSelecionada.value),carregarForm() }}  
                            />
                            <div className="invalid-feedback">*Este campo é obrigatório </div>
                        </div>
                    </div>

                    <div className="col-md-11 col-lg-4">
                        <div className="rounded shadow bg-white p-3">
                            <h3 className="fs-5 fw-bold pb-3">Detalhe da reserva</h3>
                            <div className="imagem d-flex justify-content-center sem-imagem" >
                                <img src={produto.imagensLista[0]?.url} className="img-fluid w-100 object-fit-cover rounded-1" alt={produto.imagensLista[0]?.titulo} />
                            </div>
                            <div className="py-4">
                                <p className="m-0 text-secondary ">{produto.categoria.descricao}</p>
                                <p className="m-0 fs-5 fw-bold" >{produto.nome}</p>
                                <Star quantidade={produto.qualificacao} />
                                <p className="m-0 py-4 pb-5 fw-bold border-bottom" > <FontAwesomeIcon icon={faLocationDot} className="pe-1" /> {produto.cidade.nome}, {produto.cidade.pais}</p>
                                <p className="m-0 pt-5 pb-4 fw-bold border-bottom" > check-in   <span className="float-end">{format(date[0],'P', { locale: ptBR }) }</span></p>
                                <p className="m-0 pt-5 pb-4 fw-bold border-bottom" > check-out  <span className="float-end">{format(date[1],'P', { locale: ptBR }) }</span> </p>
                            </div>
                            <div className="row mt-4 pb-2 px-3">
                                <button type="button" className="btn btn-primary text-white p-3 px-4" onClick={()=>confirmarReserva()}> {!done ? 'Confirmar reserva' : <FontAwesomeIcon icon={faSpinner} spinPulse /> } </button>
                            </div>
                        </div>
                    </div>
                </div>
                <DetalhesPoliticas dados={produto}/>
            </div>
            : <Loading />
            : <MensagemBemSucedida dados={novaReserva}/>
        }
        </BaseTemplate>
    );
};
export default Reserva;