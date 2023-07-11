/* eslint-disable react/prop-types */
import "./styles.css"
import Navbar from "../../componentes/navbar"
import Footer from "../../componentes/footer"
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const BaseTemplate = (props) => {
    return(
        <>
        <Navbar />
            <section className=" overflow-auto">
                <ToastContainer theme="dark" />
                {props.children}
            </section>
        <Footer />
        </>
    )
}

export default BaseTemplate;