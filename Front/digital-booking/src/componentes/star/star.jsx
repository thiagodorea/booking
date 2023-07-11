/* eslint-disable react/prop-types */
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const Star = (props) =>{

    const items  = [...(new Array(props.quantidade).keys())];
    const total = [...(new Array(5-props.quantidade).keys())];
    
    return(
        <>
        {items.map((index) => (
            <FontAwesomeIcon key={index} icon={faStar} className="text-primary" />    
            ))}
        {total.map((index) => (
            <FontAwesomeIcon key={index} icon={faStar} className="text-dark-light" /> 
        ))}
        </>
    )

}

export default Star;