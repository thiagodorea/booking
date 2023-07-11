import './styles.css'
import ImgLogo from "/db.png";
import { menus } from '../../../dados.json'
import { Link, useNavigate } from 'react-router-dom';
import { useContext, useEffect } from 'react';
import { AuthContext } from '../../contexts/auth-context';
import { faFacebook, faInstagram, faLinkedinIn, faTwitter } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink } from 'react-router-dom';


const Navbar = () => {

    const dataContext = useContext(AuthContext) 
    const navigate = useNavigate();
    
    const iniciarSessao = ()=>{
        navigate('/iniciar_sessao');
    }

    const criarConta = ()=> {
        navigate('/criar_conta');
    }

    const sair = () => {
        dataContext.removeData();
        dataContext.autenticado();
        dataContext.setIniciarSessao(false);
        dataContext.setCriarConta(false);
    }

    useEffect(()=>{
        dataContext.setIniciarSessao(false);
        dataContext.setCriarConta(false);
        if(dataContext.autenticado()){
            dataContext.getToken()
        }
    },[]);

    return (               
        <nav className="navbar bg-white shadow-sm">
            <div className="container-fluid d-flex align-items-end">
                <div className="d-flex">
                <Link to={"/"} className='navbar-brand d-flex align-items-end p-0'>
                    <img src={ImgLogo} alt="Logo da empresa Digital Booking" />  
                    <span className="navbar-brand p-0 ps-2 fs-5 fst-italic text-secondary d-none d-sm-block" >Sinta-se em casa</span>
                </Link>
                </div>
                
                <div className="d-none d-sm-block">
                    <div className="d-flex">
                        {!dataContext.logado ? 
                            <div >
                                {!dataContext.criarConta ?<button type="button" className="mx-2 btn btn-outline-primary btn-nav" onClick={() => criarConta()}>Criar conta</button> : null}
                                {!dataContext.iniciarSessao ? <button type="button" className="mx-2 btn btn-outline-primary btn-nav" onClick={() => iniciarSessao()} >Iniciar sessão</button> : null}
                            </div>
                        : 
                            <div className='hstack gap-2' >
                                <ul className=' navbar-nav hstack gap-3 border-end border-primary border-3 pe-2'>
                                {menus.map((menu) =>
                                    menu.private && dataContext.funcao == 'ADMIN' ? (
                                        <li className='nav-item fw-bold' key={menu.id}> <NavLink to={menu.link} className="nav-link"> {menu.title} </NavLink> </li>
                                    ) :
                                    !menu.private && (
                                        <li className='nav-item fw-bold' key={menu.id}> <NavLink to={menu.link} className="nav-link"> {menu.title} </NavLink> </li>
                                    )
                                    )}
                                </ul>

                                {dataContext.menu != null ? <span className="vr"></span> : null}
                                <span className='text-white fw-bold fs-6 text-center pt-2 avatar'>{dataContext.iniciais}</span>
                                <span className='fw-bold fs-6 lh-sm'>Olá, <br /><span className='text-primary'>{dataContext.usuarioNome} {dataContext.usuarioSobrenome}</span></span>
                                <button type="button" className="btn-close" aria-label="Close" title="Sair" onClick={() => sair()}></button>
                            </div>
                        }
                    </div>
                </div>
              {/* navBar mobile */}
                <div className="d-block d-sm-none">
                    <div className="d-flex ">
                        <button className="navbar-toggler"  data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                    </div>
                </div>
                <div className="offcanvas offcanvas-end d-block d-sm-none" tabIndex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">
                    <div className="offcanvas-header text-white bg-primary position-relative h-25">
                        <button type="button" className="btn-close btn-close-white position-absolute top-0 start-0 m-2 fs-5" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                        {!dataContext.logado ? 
                            <h5 className="offcanvas-title position-absolute bottom-0 end-0 mb-1 me-2 fs-3" id="offcanvasDarkNavbarLabel">MENU</h5>
                        :
                            <>
                            <div className='text-white fw-bold fs-5 text-center pt-1 avatar position-absolute end-0  mt-5 me-3'>{dataContext.iniciais}</div>
                            <div className='text-end position-absolute bottom-0 end-0 mb-2 me-3 '>
                                <span className='fw-bold fs-5 lh-sm'>Olá, <br /><span className='text-secondary'>{dataContext.usuarioNome} {dataContext.usuarioSobrenome}</span></span>
                            </div>
                            </>
                        }
                    </div>
                    <div className="offcanvas-body ">
                        {!dataContext.logado ? 
                        <ul className="navbar-nav justify-content-end flex-grow-1  fs-5 fw-bold text-end">
                            {!dataContext.criarConta ?
                                <li className="nav-item py-2">
                                <Link to={'/criar_conta'} className="nav-link">Criar conta </Link>
                                </li> 
                            : null}
                            {!dataContext.iniciarSessao?
                                <li className={dataContext.criarConta ? 'nav-item py-2':'border-top border-primary nav-item py-2'} >
                                    <Link to={'/iniciar_sessao'} className="nav-link" >Fazer login</Link>
                                </li>
                            : null}
                        </ul>
                        : 
                        <ul className="navbar-nav justify-content-end flex-grow-1  fs-5 fw-bold text-end">
                            {menus.map((menu) =>
                                menu.private && dataContext.funcao == 'ADMIN' ? (
                                    <li className='border-top border-primary nav-item py-2' key={menu.id}> <NavLink to={menu.link} className="nav-link"> {menu.title} </NavLink> </li>
                                ) :
                                !menu.private && (
                                    <li className="nav-item py-2" key={menu.id}> <NavLink to={menu.link} className="nav-link"> {menu.title} </NavLink> </li>
                                )
                            )}
                        </ul>
                        }
                    </div>
                    
                    <div className="offcanvas-footer fs-1 text-end position-absolute bottom-0 end-0">
                    {dataContext.logado ?
                        <p className="fs-5 p-2 border-bottom border-primary" >Deseja <span className='text-primary' onClick={() => sair()} data-bs-dismiss="offcanvas">encerrar a sessão</span> ? </p>: null}
                        <FontAwesomeIcon icon={faFacebook} className='m-2' />
                        <FontAwesomeIcon icon={faLinkedinIn} className='m-2' />
                        <FontAwesomeIcon icon={faTwitter} className='m-2' />
                        <FontAwesomeIcon icon={faInstagram} className='m-2'/>
                    </div>
                </div>     
            {/* --------------------------  */}
            </div>
        </nav>
    );
};

export default Navbar;
