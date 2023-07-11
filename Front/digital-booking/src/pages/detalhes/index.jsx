import BaseTemplate from '../../templates/base-template';
import DetalhesHeader from '../../componentes/detalhes/detalhes-header';
import DetalhesClassificacao from '../../componentes/detalhes/detalhes-classificacao';
import DetalhesDecricao from '../../componentes/detalhes/detalhes-descricao';
import DetalhesCaracteristicas from '../../componentes/detalhes/detalhes-caracteristicas';
import DetalhesImagens from '../../componentes/detalhes/detalhes-imagens';
import DetalhesPoliticas from '../../componentes/detalhes/detalhes-politica';
import DetalhesDatasDisponiveis from '../../componentes/detalhes/detalhes-datas';
import DetalhesLocalizacao from '../../componentes/detalhes/detalhes-localizacao';
import api from '../../service/api';
import Loading from '../../componentes/Loading';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';



const Detalhes = () => {

    const {uidProduto} = useParams();

    const getProduto = async () => {
        try {
            const response = await api.get(`/produtos/${uidProduto}`)
            setProduto(response.data)
        } catch (error) {
            toast.error('Erro ao buscar o produto.')
        }
    }

    const [produto, setProduto] = useState();
    
    useEffect(() => {
        getProduto();
    },[])
    return (    
        <>
        <BaseTemplate>
            { produto ? 
                <>
                <DetalhesHeader dados={produto} />
                <DetalhesClassificacao dados={produto} />
                <DetalhesImagens dados={produto.imagensLista} />
                <DetalhesDecricao dados={produto} />
                <DetalhesCaracteristicas dados={produto} />
                <DetalhesDatasDisponiveis dados={produto}/>
                <DetalhesLocalizacao dados={produto} />
                <DetalhesPoliticas dados={produto}/>
                </>
                : <Loading />
            }
        </BaseTemplate>
        </>           
    );
};

export default Detalhes;
