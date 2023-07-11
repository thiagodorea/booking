/* eslint-disable react/prop-types */
import Card from "../card";

const Recomendacao = (props) => {

    const produtos = props.dados;
    
    return (
        <div className="container-fluid bg-light">
                <div className="row d-flex justify-content-center">
                    <div className="col-11">
                    <h2 className="fs-4 fw-bold text-secondary mt-3">{produtos.origem}</h2>
                        <Card dados={produtos}/>
                    </div>
                </div>
        </div>
    );
};
export default Recomendacao;