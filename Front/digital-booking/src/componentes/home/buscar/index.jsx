import ReactDatePicker from "react-datepicker";
import Select from 'react-select'
import api from "../../../service/api";
import { faCalendarDays, faLocationDot } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useContext, useEffect, useState } from "react";
import { toast } from "react-toastify";
import { AuthContext } from "../../../contexts/auth-context";
import { format } from "date-fns";
import { ptBR } from "date-fns/locale";




const Buscar = () => {

    const dataContext = useContext(AuthContext);
    
    const [ listaCidades, setListaCidades ] = useState([]);
    const [ selecionouNovaCidade, setSelecionouNovaCidade ] = useState();
    const [ uidCidade, setUidCidade ] = useState(null);
    const [ dateRange, setDateRange ] = useState([null, null]);
    const [ startDate, endDate ] = dateRange;
    const [ isClearable ] = useState(true);

    const theme = theme => ({
        ...theme,
        colors: {
            ...theme.colors,
            primary50: "#F0572D",
            primary25: "#FFBDAD",
            primary: "#F0572D"
        }
    });

    const getCidades = async () => {
        try {
            const response = await api.get("/cidades")
            setListaCidades(response.data);
            } catch (error) {
            toast.error('Erro ao carregar as cidade.')
        }
    }

    const buscar = async (event) => {
        event.preventDefault();
        let url;
        if(uidCidade != null || startDate != null){
            uidCidade != null && startDate != null ? url = `/produtos/filtro?cidade=${uidCidade}&dataInicial=${format(startDate,'yyyy-MM-dd')}&dataFinal=${format(endDate,'yyyy-MM-dd')}`:
            uidCidade != null ? url = `/produtos/filtro?cidade=${uidCidade}` :  url = `/produtos/filtro?dataInicial=${format(startDate,'yyyy-MM-dd')}&dataFinal=${format(endDate,'yyyy-MM-dd')}` ;
            try {
                const response = await api.get(url)
                dataContext.setFiltraPorCidade(response.data)
            } catch (error) {
                toast.error('Não conseguimos buscar os hotéis, por favor tente mais tarde.')
            }
        }else{dataContext.setFiltraPorCidade()}
    }

    const ListaCidades = 
        listaCidades.map((cidade) =>(
            {value: cidade.uid, label:cidade.nome}
        ))

    useEffect(() => {
        getCidades();
        dataContext.setDataCheckInCheckOut([null,null])
    },[])

    return (
        <div className="container-fluid bg-secondary">
                <div className="row d-flex justify-content-center py-4">
                    <div className="col-11">
                        <h1 className="text-center fs-2 fw-bold text-white">Buscar ofertas em hotéis, casas e muito mais</h1>
                        
                            <form className="row py-3 d-flex justify-content-center gap-2">
                                <div className="col-sm-4 col-xs-12 position-relative  ">
                                    <label htmlFor="aondeVamos" className="visually-hidden"></label>
                                    <FontAwesomeIcon icon={faLocationDot} className="position-absolute p-2 fs-4 z-3"  />
                                    <Select options={ListaCidades} placeholder= "Aonde Vamos?" id="aondeVamos" className="form-control p-0 z-2" 
                                    theme={theme}
                                    isClearable={isClearable}
                                    onChange={(newValue) =>{ setUidCidade(newValue?.value),setSelecionouNovaCidade(true)}}
                                    value={dataContext.filtraPorCidade && !selecionouNovaCidade? ListaCidades.filter(cidade =>cidade.value == dataContext.filtraPorCidade[0]?.cidade.uid ):undefined}
                                    />
                                </div>
                                <div className="col-sm-5 col-xs-12 position-relative datepicker-desktop">
                                    <FontAwesomeIcon icon={faCalendarDays} className="position-absolute p-2 fs-4 z-1"/>
                                    <ReactDatePicker
                                        monthsShown={2}
                                        minDate={new Date()}
                                        selectsRange={true} startDate={startDate} endDate={endDate} 
                                        onChange={(update) => { setDateRange(update);dataContext.setDataCheckInCheckOut(update) }} 
                                        isClearable={true} showIcon={false} dateFormat={"dd/MM/yyyy"} locale={ptBR}
                                        className="form-control ps-5 py-2 shadow" placeholderText="Check in - Check out"
                                        />
                                </div>
                                <div className="col-sm-5 col-xs-12 position-relative datepicker-device">
                                    <FontAwesomeIcon icon={faCalendarDays} className="position-absolute p-2 fs-4 z-1"/>
                                    <ReactDatePicker
                                        minDate={new Date()}
                                        selectsRange={true} startDate={startDate} endDate={endDate} 
                                        onChange={(update) => { setDateRange(update);dataContext.setDataCheckInCheckOut(update) }} 
                                        isClearable={true} showIcon={false} dateFormat={"dd/MM/yyyy"} locale={ptBR}
                                        className="form-control ps-5 py-2 shadow" placeholderText="Check in - Check out"
                                        />
                                </div>
                                <div className="col-sm-2 col-xs-12 d-grid">
                                    <button className="btn btn-primary text-white" onClick={() => buscar(event)} >Buscar</button>
                                </div>
                            </form>
                        
                    </div>
                </div>
        </div>
    );
};
export default Buscar;