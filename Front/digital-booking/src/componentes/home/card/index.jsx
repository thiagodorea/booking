/* eslint-disable react/prop-types */
import "./styles.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCar, faHeart, faKitchenSet, faLocationDot, faPaw, faPersonSwimming, faSnowflake, faTv, faWifi } from "@fortawesome/free-solid-svg-icons";
import { useContext, useEffect, useState } from "react";
import { useNavigate,useLocation } from "react-router-dom";
import Star from "../../star/star";
import Loading from "../../Loading";
import { AuthContext } from "../../../contexts/auth-context";


const Card = (props) => {
    
    const dataContext = useContext(AuthContext) 
    const dadosProps = props.dados;
    const navigate = useNavigate();
    
    const verMais = (uid) => {
        navigate(`/produto/${uid}`);
    }

    const [ produtos, setProdutos ] = useState([]);

    useEffect(()=>{
        setProdutos(dadosProps.dados)
    },[dadosProps])

    return (
        <div className="row row-cols-1 row-cols-lg-2">
            {!dadosProps.done ? <Loading /> : produtos.length != 0 ?
                <>
                { produtos.map((produto) =>(
                    <div className="col" key={produto.uid}>
                        <div className="card mb-3 rounded-4">
                            <div className="row g-0">
                                <div className="col-md-5 position-relative" >
                                    <FontAwesomeIcon icon={faHeart} className="position-absolute top-0 end-0 text-light fs-5 m-3" />
                                    <img src={produto.imagensLista[0].url} className="img-fluid rounded-start-4 h-100 object-fit-cover d-none d-sm-block" alt={produto.imagensLista[0].titulo} />
                                    <img src={produto.imagensLista[0].url} className="img-fluid rounded-top-4 h-100 object-fit-cover d-block d-sm-none" alt={produto.imagensLista[0].titulo} />
                                </div>
                                <div className="col-md-7">
                                    <div className="card-body">
                                        <div className="row pb-3">
                                            <div className="col-8 col-md-8">
                                                <p className="card-text fs-6 fw-bold m-0 text-dark-light">
                                                    <small>{produto.categoria.descricao}
                                                    <span className="ps-1"> <Star quantidade={produto.qualificacao} /> </span> 
                                                    </small>
                                                </p>
                                                <h5 className="card-title fw-bold">{produto.nome}</h5>
                                                { dataContext.funcao == 'ADMIN' && dataContext.logado ? <p className="subtitulo m-0 p-0 lh-1">{produto.uid}</p> : null}
                                            </div>
                                            <div className="col-4 col-md-4 fw-bold lh-sm text-end">
                                                <p className="fs-4 text-center rounded-3 bg-secondary text-light ms-auto mb-0 pe-4 ps-2 col-1 ">{produto.classificacao}</p>
                                                <p className="m-0 fs-6 text-end"><small >{produto.classificacao < 8 ? 'Bom': produto.classificacao == 8 ? 'Muito Bom':"Excelente" } </small></p>
                                            </div>
                                        </div>
                                        <p className="card-text fs-6 mb-1"><small> <FontAwesomeIcon icon={faLocationDot}  className="pe-1"/> 
                                        {produto.cidade.nome}, {produto.cidade.pais}
                                        <span className="text-primary"> </span></small></p>
                                        <p className="card-text hstack gap-2">
                                            {produto.caracteristicasLista.map((caract) =>(
                                                <span key={caract.uid}>
                                                <FontAwesomeIcon  icon={
                                                    caract.icone == 'faKitchenSet'? faKitchenSet : 
                                                    caract.icone == 'faTv'? faTv : 
                                                    caract.icone == 'faSnowflake'? faSnowflake : 
                                                    caract.icone == 'faPaw'? faPaw : 
                                                    caract.icone == 'faCar'? faCar : 
                                                    caract.icone == 'faPersonSwimming'? faPersonSwimming : faWifi
                                                    }
                                                /> 
                                                </span>
                                            ))}
                                        </p>
                                        <p className="card-text">
                                            <span className="text">{produto.descricao}</span>
                                            {/* <span className="text-primary">mais...</span> */}
                                        </p>
                                        <div className="d-grid">
                                            <button className="btn btn-primary shadow-sm text-white" onClick={() => verMais(produto.uid)} >Ver mais</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                ))
                } 
                </>
                : <div className="col" ><h5 >{ dadosProps.origem == 'Recomendações'? "Não encontramos hospedagem disponível para a cidade informada neste periodo." : "Ainda não fez uma reserva." }</h5> </div> 
            }
        </div>
    );
};
export default Card;