/* eslint-disable react/prop-types */
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import './styles.css'
import { CircularProgressbar } from 'react-circular-progressbar';
import { faCircleCheck, faCircleXmark, faLink } from '@fortawesome/free-solid-svg-icons';

const FileList = ({files}) => {
    return(
        <ul className="p-0">
            {files.map(uploadedFile => (
                <li key={uploadedFile.id} className='d-flex justify-content-between align-items-center m-3 '>
                    <div className='d-flex flex-row align-items-center'>
                        <div className='preview' style={{backgroundImage: `url("${uploadedFile.preview}")` }} >
                        </div> 
                        <div className='d-flex flex-column'>
                            <strong>{uploadedFile.name}</strong>
                            <span>{uploadedFile.readableSize}
                                {!!uploadedFile.url && (<button className='btn btn-sm text-danger fw-bold' onClick={() => {}} >Excluir</button>)}
                            </span>
                        </div>
                    </div>
                    <div>
                        {!uploadedFile.uploaded && !uploadedFile.error && (
                            <CircularProgressbar styles={{ root: {width: 34}, path: { stroke: '#F0572D'} }}
                            strokeWidth={15} value={uploadedFile.progress}
                            className='mx-1'
                        />
                        )}
                        
                        {uploadedFile.url &&(<a href={uploadedFile.url} target='_blank' rel='noopemer noreferrer' >
                            <FontAwesomeIcon icon={faLink} className='mx-1' />
                        </a>)}
                        {uploadedFile.uploaded && <FontAwesomeIcon icon={faCircleCheck} className='text-success mx-1' />}
                        {uploadedFile.error && <FontAwesomeIcon icon={faCircleXmark} className='text-danger mx-1' /> }
                    </div>
                </li>
            ))}
        </ul>
        
    )

}

export default FileList;