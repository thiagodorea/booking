/* eslint-disable react/prop-types */
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Star from "../../star/star";

const DetalhesClassificacao = (props) =>{

    const produto = props.dados;

    return(
        <div className="container-fluid">
            
            <div className='row py-1 bg-light d-flex justify-content-center'>
                <div className="col-11 d-flex gap-2 fw-bold">
                    <div className="col-auto"> <FontAwesomeIcon icon={faLocationDot} /></div>
                    <div className="col"> {produto.endereco}, {produto.cidade.nome}, {produto.cidade.pais}</div>
                    <div className="col-auto d-flex align-items-baseline">
                        <div className="row d-flex align-items-center">
                            <div className="col-auto px-2">
                                <p className="fs-6 fw-bold m-0 text-dark-light text-end">
                                {produto.classificacao < 8 ? 'Bom': produto.classificacao == 8 ? 'Muito Bom':"Excelente" }
                                </p>
                                <p className='m-0'>
                                    <Star quantidade={produto.qualificacao} />
                                </p>
                            </div>
                            <div className="col-auto p-0 ">
                                <p className="fs-4 text-center rounded-3 bg-secondary text-light mb-0 pe-4 ps-2 col-1 ">{produto.classificacao}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default DetalhesClassificacao
;