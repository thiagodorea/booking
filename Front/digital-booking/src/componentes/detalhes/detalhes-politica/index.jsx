/* eslint-disable react/prop-types */
const DetalhesPoliticas = (props) =>{

    const produto = props.dados;    
    return(
        <div className="container-fluid py-2">
            <div className="row d-flex justify-content-center">
                <div className="col-11 border-bottom border-primary">
                    <h2 className='fs-4 fw-bold '>O que você precisa saber:</h2>
                </div>
                <div className="col-11 py-3">
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-3">
                    <div className="col"> 
                        <h3 className="fs-5 fw-bold pb-3" >Regras</h3>
                        <p className="mb-2" > {produto?.regras} </p>
                    </div>
                    <div className="col"> 
                        <h3 className="fs-5 fw-bold pb-3" >Saúde e segurança</h3>
                        <p className="mb-2" > {produto?.saudeSeguranca} </p>
                    </div>
                    <div className="col"> 
                        <h3 className="fs-5 fw-bold pb-3" >Política de cancelamento</h3>
                        <p className="mb-2" > {produto?.politicaCancelamento} </p>
                    </div>
                </div>
                </div>
            </div>
        </div>
    )
}
export default DetalhesPoliticas;