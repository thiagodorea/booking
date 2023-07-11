/* eslint-disable react/prop-types */
import "./styles.css"
import ImageGallery from 'react-image-gallery';

const DetalhesImagens = (props) =>{

    const imagens = props.dados ;

    const images = imagens.map((img) => ( 
            {
                original:img.url,
                thumbnail:img.url
            }    
        ));

    return(
        <div className="container-fluid py-2">
            <div className="row d-flex justify-content-center">
                <div className="col-11 d-none d-lg-block">
                    <div className="row row-cols-2 ">
                        <div className="col">
                            <img src={imagens[0].url} className="img-fluid w-100 object-fit-cover rounded-4" alt={imagens[0].titulo} style={{height:'450px'}} />
                        </div>
                        <div className="col position-relative">
                            <div className="row row-cols-2 gy-3">
                                <div className="col sem-imagem">
                                    <img src={imagens[1]?.url} className="img-fluid w-100 object-fit-cover rounded-4" alt={imagens[1]?.titulo} style={{height:'217px'}} />
                                </div>
                                <div className="col sem-imagem"  >
                                    <img src={imagens[2]?.url} className="img-fluid w-100 object-fit-cover rounded-4" alt={imagens[2]?.titulo} style={{height:'217px'}} />
                                </div>
                                <div className="col sem-imagem">
                                    <img src={imagens[3]?.url} className="img-fluid w-100 object-fit-cover rounded-4" alt={imagens[3]?.titulo} style={{height:'217px'}} />
                                </div>
                                <div className="col sem-imagem">
                                    <img src={imagens[4]?.url} className="img-fluid w-100 object-fit-cover rounded-4" alt={imagens[4]?.titulo} style={{height:'217px'}} />
                                </div>
                            </div>
                                <a className="btn btn-link link-dark bg-white bg-opacity-75 link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover position-absolute bottom-0 end-0 pe-4 pb-2 fw-bold fs-5"
                                data-bs-toggle="modal" data-bs-target="#galeria"
                                >Ver Mais</a>
                        </div>
                    </div>
                </div>
                <div className="col-11 d-block d-lg-none">
                    <div id="carouselImagens" className="carousel slide">
                        <div className="carousel-indicators">
                        {imagens.map((img,index) =>(
                            <div key={img.uid}>
                                <button type="button" data-bs-target="#carouselImagens" data-bs-slide-to={index} className= {index == 0 ? "active" : null} aria-label={"Slide "+ index+1}></button>
                            </div>
                            ))}
                        </div>
                        <div className="carousel-inner">
                            {imagens.map((img,index) =>(
                                <div key={img.uid}>
                                    <div  className={index == 0 ? "carousel-item active" : "carousel-item"} style={{height:"300px"}}>
                                        <img src={img.url} className="d-block w-100 h-100 object-fit-cover" alt={img.titulo}></img>
                                    </div>
                                </div>
                            ))}
                        </div>
                        <button className="carousel-control-prev" type="button" data-bs-target="#carouselImagens" data-bs-slide="prev">
                            <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Previous</span>
                        </button>
                        <button className="carousel-control-next" type="button" data-bs-target="#carouselImagens" data-bs-slide="next">
                            <span className="carousel-control-next-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
            </div>
            {/* -- Modal -- */}
            <div className="modal fade" id="galeria" data-bs-backdrop="true" data-bs-keyboard="true" tabIndex="-1" aria-labelledby="galeriaLabel" aria-hidden="true" >
                <div className="modal-dialog modal-dialog-centered modal-lg">
                    <div className="modal-content">
                        <div className="modal-body " >
                            <ImageGallery items={images}  />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default DetalhesImagens;