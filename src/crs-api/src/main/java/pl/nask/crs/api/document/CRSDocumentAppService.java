package pl.nask.crs.api.document;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.DocumentSettingsVO;
import pl.nask.crs.api.vo.DocumentUploadVO;
import pl.nask.crs.api.vo.UploadFilenameVO;
import pl.nask.crs.api.vo.UploadPurposeVO;
import pl.nask.crs.api.vo.UploadResultVO;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSDocumentAppService {

    @WebMethod
    DocumentSettingsVO getDocumentSettings(
            @WebParam(name = "user") AuthenticatedUserVO user)
        throws AccessDeniedException;

    @WebMethod
    List<UploadResultVO> handleUpload(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "documents") List<DocumentUploadVO> documents,
            @WebParam(name = "purpose") UploadPurposeVO purpose)
    throws AccessDeniedException, DocumentGeneralException, EmailSendingException;

    @WebMethod
    void handleMailUpload(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "replyTo") String replyTo,
            @WebParam(name = "content") String emailContent,
            @WebParam(name = "attachments") List<UploadFilenameVO> attachments)
        throws AccessDeniedException;
}
