/* eslint-disable react/prop-types */
const DetalhesDecricao = (props) =>{

    const produto = props.dados;

    return(
        <div className="container-fluid py-2">
            <div className="row d-flex justify-content-center">
                <div className="col-11">
                    <p className='fs-6 '>{produto.descricao} </p>
                </div>
            </div>
        </div>
    )
}
export default DetalhesDecricao;