/* eslint-disable react/prop-types */
import "./styles.css"
import { useContext, useEffect, useState } from "react";
import  'react-calendar/dist/Calendar.css' ;
import { Calendar } from 'react-calendar'
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDays } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../../contexts/auth-context";



const DetalhesDatasDisponiveis = (props) =>{
    
    const dataContext = useContext(AuthContext);
    const produto = props.dados;
    const navigate = useNavigate();

    const reservarHotel = (uid) =>  {
        navigate(`/produto/${uid}/reserva`,{state:date});
    }
    const [date, setDate] = useState(new Date());

    useEffect(() =>{
        if(dataContext.dataCheckInCheckOut[0] != null && dataContext.dataCheckInCheckOut[1] != null ){
            setDate([new Date (dataContext.dataCheckInCheckOut[0]) ,new Date (dataContext.dataCheckInCheckOut[1])  ]);
        }
    },[])

    return(
        <div className="container-fluid py-2 bg-light">
            <div className="row d-flex justify-content-center">
                <div className="col-11 ">
                    <h2 className='fs-4 fw-bold '>Datas disponíveis</h2>
                </div>
                <div className="col-11 py-3 ">
                    <div className="row d-flex justify-content-center align-items-center gap-4">
                        <div className="col-md-12 col-lg-8">
                            <Calendar onChange={setDate} value={date} selectRange={true} calendarType={'Hebrew'} 
                            className="d-none d-sm-block shadow "
                            minDate={new Date()}
                            showDoubleView
                            showNeighboringMonth={false}
                            showFixedNumberOfWeeks={false}
                            />
                            <Calendar onChange={setDate} value={date} selectRange={true} calendarType={'Hebrew'} 
                            className="d-block d-sm-none shadow "
                            minDate={new Date()}
                            showNeighboringMonth={false}
                            showFixedNumberOfWeeks={false}
                            />
                        </div>
                        <div className="col-md-7 col-lg-3 bg-white shadow rounded p-3 d-grid">
                            <p className="m-0 text-primary">Período escolhido</p>
                            <p>
                            <FontAwesomeIcon icon={faCalendarDays} className="text-dark-light pe-2"/> 
                            {date.length > 0 ? 
                            <>
                            {format(date[0],'P', { locale: ptBR }) } <span className="px-1">|</span> {format(date[1],'P', { locale: ptBR })}
                            </>
                            : null}
                            </p>
                            <p>Adicione as datas da sua viagem para obter preços exatos</p>
                            <button disabled={date.length !== 2 || date == null} className="btn btn-primary text-white" onClick={() => reservarHotel(produto.uid,date[0],date[1])}> Iniciar Reserva </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default DetalhesDatasDisponiveis;