import "./styles.css"
import BaseTemplate from "../../templates/base-template";
import Buscar from "../../componentes/home/buscar";
import BuscarPorAcomodacao from "../../componentes/home/buscarPorAcomodacao";
import Recomendacao from "../../componentes/home/recomendacao";
import api from "../../service/api";
import { toast } from "react-toastify";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/auth-context";


const Home = () =>{
    
    const dataContext = useContext(AuthContext);

    const [produtos, setProdutos] = useState([]);
    const [done, setDone] = useState(false);

    const handleprops = {
        origem: "Recomendações",
        dados: produtos,
        done:done,
    }

    useEffect(() => {
        dataContext.filtraPorCidade ? (setProdutos(dataContext.filtraPorCidade))
            : dataContext.filtraPorCategoria ? (setProdutos(dataContext.filtraPorCategoria))
            : getProdutos()
    },[dataContext.filtraPorCidade, dataContext.filtraPorCategoria])


    const getProdutos = async () => {
        try {
            const response = await api.get("/produtos");
            setProdutos(response.data);
            setDone(true);
        } catch (error) {
            toast.error('Erro ao buscar os produtos.');
            setDone(true);
        }
    }

    return(
        <>
        <BaseTemplate>
            <Buscar />
            <BuscarPorAcomodacao />
            <Recomendacao dados={handleprops} />
        </BaseTemplate>
        </>
    )
}
export default Home;