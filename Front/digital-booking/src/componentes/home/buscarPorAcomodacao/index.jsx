import { useContext, useEffect, useState } from "react";
import api from "../../../service/api";
import { toast } from "react-toastify";
import Loading from "../../Loading";
import { AuthContext } from "../../../contexts/auth-context";

const BuscarPorAcomodacao = () => {

    const getCategorias = async () => {
        try {
            const response = await api.get("/categorias")
            setCategorias(response.data)
            setDone(true);
        } catch (error) {
            toast.error('Erro ao buscar Categorias.')
            setDone(true);
        }
    }

    const filtraProCategoria = async (uidCategoria, active) => {
        setCategoriaSelecionada(uidCategoria)
        if(active){
            try {
                const response = await api.get(`/produtos/${uidCategoria}/categoria`);
                dataContext.setFiltraPorCategoria(response.data)
            } catch (error) {
                toast.error('Erro ao buscar os produtos da categoria.')
            }
        }else{ setCategoriaSelecionada(''),dataContext.setFiltraPorCategoria()}
    }

    const dataContext = useContext(AuthContext);

    const [ categorias, setCategorias ] = useState([]);
    const [ categoriaSelecionada, setCategoriaSelecionada ] = useState('');
    const [ done, setDone ] = useState(false);

    
    useEffect(() => {
        getCategorias();
        if(dataContext.filtraPorCategoria)
        setCategoriaSelecionada(dataContext.filtraPorCategoria[0].categoria.uid)
    },[])

    return (
        <div className="container-fluid py-4">
            <div className="row d-flex justify-content-center">
                <div className="col-11">
                    <h2 className="fs-4 fw-bold text-secondary">Buscar por tipo de acomodação</h2>
                    {!done ? <Loading /> :
                    <div className="row row-cols-1 row-cols-md-2 row-cols-xl-4 gy-3">
                        {categorias.map((categoria) =>(
                            <div className="col" key={categoria.uid}>
                            <div className="card rounded-4 shadow ">
                                <button type="button" className={categoria.uid == categoriaSelecionada ? "btn btn-outline-primary rounded-4 p-0 text-start active": "btn btn-outline-primary rounded-4 p-0 text-start"} style={{border:"none"}} data-bs-toggle="button" onClick={() => categoria.uid == categoriaSelecionada ? filtraProCategoria(categoria.uid,false) :  filtraProCategoria(categoria.uid,true)} >
                                    <div className="row">
                                        <div className="col-md-12">
                                            <img src={categoria.urlImagem} className="card-img-top rounded-top-4 object-fit-cover" alt="Imagem de um hotel" />
                                            <div className="card-body">
                                                <h5 className="card-title fw-bold fs-5 m-0">{categoria.descricao}</h5>
                                                <p className="card-text fs-6"><small>807.105 {categoria.descricao}</small></p>
                                            </div>    
                                        </div>
                                    </div>
                                </button>
                            </div>
                        </div>
                        ))}
                    </div>
                    }
                </div>
            </div>
        </div>
    );
};
export default BuscarPorAcomodacao;