import { Link } from "react-router-dom";
import BaseTemplate from "../../templates/base-template";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft } from "@fortawesome/free-solid-svg-icons";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/auth-context";
import api from "../../service/api";
import { toast } from "react-toastify";
import Recomendacao from "../../componentes/home/recomendacao";

const MinhasReservas = () =>{

    const dataContext = useContext(AuthContext);
    const [ reservas, setReservas ] = useState([]);
    const [ done, setDone ] = useState(false);
    
    const handleprops = {
        origem: "Reservas",
        dados: reservas,
        done: done
    }

    useEffect(()=>{
        if(dataContext.usuarioNum != ''){
            getReservas()
        }
    },[dataContext,])
    
    
    const getReservas = async () => {
        try {
            const response = await api.get(`/produtos/${dataContext.usuarioNum}/reserva/usuario`);
            setReservas(response.data);
            setDone(true);
        } catch (error) {
            toast.error('Erro ao buscar Reservas.')
            setDone(true);
        }
    }

    return(
        <BaseTemplate>
            <div className="container-fluid px-0">
                <div className='py-2 bg-secondary text-white d-flex justify-content-center'>
                    <div className="col-11 d-flex align-items-center">
                        <div className='col'>
                            <h1 className='fs-4 m-0'> Minhas Reservas </h1>
                        </div>
                        <div className='col-auto text-end'>
                            <Link to={-1}> <FontAwesomeIcon icon={faAngleLeft} className='fs-2 text-white' /> </Link>
                        </div>
                    </div>
                </div>
            </div>
            <Recomendacao dados={handleprops} />
        </BaseTemplate>
    )
    
}

export default MinhasReservas;