import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./styles.css";
import { faFacebook, faInstagram, faLinkedinIn, faTwitter } from "@fortawesome/free-brands-svg-icons";

const Footer = () => {
    return (
        <footer className="p-1 mt-auto bg-primary text-white">
            <div className="container-fluid d-flex justify-content-between align-items-center">
                <span className="fs-6"> ©2023 Digital Booking </span>
                <div className="fs-4 d-none d-sm-block">
                    <FontAwesomeIcon icon={faFacebook} className='m-2' />
                    <FontAwesomeIcon icon={faLinkedinIn} className='m-2' />
                    <FontAwesomeIcon icon={faTwitter} className='m-2' />
                    <FontAwesomeIcon icon={faInstagram} className='m-2'/>
                </div>
            </div>
        </footer>
    );
};
export default Footer;
