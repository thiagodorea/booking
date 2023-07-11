/* eslint-disable react/prop-types */

import { faAngleLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../../contexts/auth-context";

const DetalhesHeader = (props) =>{

    const produto = props.dados;
    const dataContext = useContext(AuthContext) 

    return(
        <div className="container-fluid px-0">
            <div className='row py-1 bg-secondary text-white d-flex justify-content-center'>
                <div className="col-11 d-flex align-items-center">
                    <div className='col'>
                        <h1 className='fs-4'>
                        <small className='fs-6' >{produto.categoria.descricao}</small><br />
                        {produto.nome}
                        </h1>
                    </div>
                    {dataContext.logado && dataContext.funcao == 'ADMIN' 
                    ?   <div className='col-auto text-end pe-3'> 
                            <Link to='/configuracao' state={produto.uid} className="btn btn-outline-light" > editar </Link>
                        </div>
                    : null
                    }
                    <div className='col-auto text-end'>
                        <Link to={-1}> <FontAwesomeIcon icon={faAngleLeft} className='fs-2 text-white' /> </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default DetalhesHeader;