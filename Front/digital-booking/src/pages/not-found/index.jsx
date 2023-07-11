import BaseTemplate from "../../templates/base-template";
import notFound from '../../assets/img/notFound.jpg'
const NotFound = () => {


    return (
        <BaseTemplate>
        <div className=" d-flex  justify-content-center h-100">
            <div className="h-75 align-self-center">
                <h1 className=" fw-bold text-center"> Ooops!</h1>
                <h2 className="fs-4 text-center"> 404 - Página não encontrada</h2>
                <div className="col-6 mx-auto">
                    <img src={notFound} alt="Imagem de pagina não encontrada" 
                    className="img-fluid"/>
                </div>
            </div>
        </div>
        </BaseTemplate>
        );
    };
export default NotFound;