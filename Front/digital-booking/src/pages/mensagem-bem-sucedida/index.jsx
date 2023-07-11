/* eslint-disable react/prop-types */
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faThumbsUp } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
const MensagemBemSucedida = (props) => {

    const dados = props.dados;

    return (
        <div className=" d-flex  justify-content-center h-100 bg-light px-4">
            <div className="h-75 align-self-center ">
            <div className="rounded-3 shadow bg-white px-5 py-4">
                <h1 className="fw-bold text-center text-primary"> <FontAwesomeIcon icon={faThumbsUp} /> </h1>
                {dados.tipo == "reserva" ? <h2 className="fs-4 fw-bold text-center text-primary"> Muito obrigado!</h2>: null }
                <h2 className="fs-5 fw-bold text-center"> {dados.mensagem}.</h2>
                <h2 className="fs-5 fw-bold text-center pt-3"> {dados.numProduto}</h2>
                {dados.tipo == "produto" ? <div className="d-grid gap-2 mx-4">
                    <Link to={`/produto/${dados.numProduto}`} type="button" className="btn btn-outline-primary px-5 mt-3 ">Visualizar</Link>
                </div>: null}
                <div className="d-grid gap-2 mx-4">
                    <Link to={"/"} type="button" className="btn btn-primary px-5 mt-3 text-white">ok</Link>
                </div>
            </div>
            </div>
        </div>
        );
    };
export default MensagemBemSucedida;