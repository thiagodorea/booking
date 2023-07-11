/* eslint-disable react/prop-types */
import { faCar, faKitchenSet, faPaw, faPersonSwimming, faSnowflake, faTv, faWifi } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const DetalhesCaracteristicas = (props) =>{

    const produto = props.dados;

    return(
        <div className="container-fluid py-2">
            <div className="row d-flex justify-content-center">
                <div className="col-11 border-bottom border-primary">
                    <h2 className='fs-4 fw-bold '>O que esse lugar oferece?</h2>
                </div>
                <div className="col-11 py-3">
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3">
                {produto.caracteristicasLista.map((caract) =>(
                                        <div key={caract.uid} className="col">
                                        <FontAwesomeIcon  icon={
                                            caract.icone == 'faKitchenSet'? faKitchenSet : 
                                            caract.icone == 'faTv'? faTv : 
                                            caract.icone == 'faSnowflake'? faSnowflake : 
                                            caract.icone == 'faPaw'? faPaw : 
                                            caract.icone == 'faCar'? faCar : 
                                            caract.icone == 'faPersonSwimming'? faPersonSwimming : faWifi
                                            }
                                            className="pe-2"
                                            /> 
                                            {caract.icone == 'faKitchenSet'? 'Cozinha' : 
                                            caract.icone == 'faTv'? 'Televisão' : 
                                            caract.icone == 'faSnowflake'? 'Ar Condicionado' : 
                                            caract.icone == 'faPaw'? 'Aceita Pets' : 
                                            caract.icone == 'faCar'? 'Estacionamento' : 
                                            caract.icone == 'faPersonSwimming'? 'Piscina' : 'Wifi'}
                                        </div>
                                    ))}
                </div>
                </div>
            </div>
        </div>
    )
}
export default DetalhesCaracteristicas;