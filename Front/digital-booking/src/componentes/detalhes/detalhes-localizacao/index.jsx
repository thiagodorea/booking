/* eslint-disable react/prop-types */
import "./styles.css"
import "leaflet/dist/leaflet.css";
import { Icon } from "leaflet";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";


const DetalhesLocalizacao = (props) =>{

    const produto = props.dados;

    const position = [produto.latitude, produto.longitude]
    const customIcon = new Icon({
        iconUrl: "../../../../hotel-location-pointers.png",    
        iconSize:[58,58],
        popupAnchor: [0, -20]
    })

    return(
        <div className="container-fluid py-2">
            <div className="row d-flex justify-content-center">
                <div className="col-11 border-bottom border-primary">
                    <h2 className='fs-4 fw-bold '>Onde você vai estar?</h2>
                </div>
                <div className="col-11 py-3">
                    <MapContainer center={position} zoom={15} scrollWheelZoom={false}>
                    <TileLayer url="https://tile.openstreetmap.org/{z}/{x}/{y}.png" />
                    <Marker position={position} icon={customIcon}>
                        <Popup> {produto.nome}<br /> {produto.endereco}, {produto.cidade.nome}, {produto.cidade.pais} </Popup>
                    </Marker>
                </MapContainer>
                </div>
            </div>
        </div>
    )
}
export default DetalhesLocalizacao;