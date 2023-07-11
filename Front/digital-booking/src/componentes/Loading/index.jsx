import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSpinner } from '@fortawesome/free-solid-svg-icons'

const Loading = () => {
    return (
        <div className="fw-bold fs-4 text-dark-light p-3">
            <p>Carregando <FontAwesomeIcon icon={faSpinner} spinPulse /> </p>
        </div>
    );
}

export default Loading;