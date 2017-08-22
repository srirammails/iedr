package pl.nask.crs.api.document;

import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.DocumentSettingsVO;
import pl.nask.crs.api.vo.DocumentUploadVO;
import pl.nask.crs.api.vo.UploadFilenameVO;
import pl.nask.crs.api.vo.UploadPurposeVO;
import pl.nask.crs.api.vo.UploadResultVO;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.documents.UploadResult;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName="CRSDocumentAppService", endpointInterface="pl.nask.crs.api.document.CRSDocumentAppService")
public class DocumentAppServiceEndpoint extends WsSessionAware implements CRSDocumentAppService {

    private DocumentAppService documentAppService;

    public void setDocumentAppService(DocumentAppService service) {
        this.documentAppService = service;
    }

    @Override
    public DocumentSettingsVO getDocumentSettings(AuthenticatedUserVO userVO) throws AccessDeniedException {
        return new DocumentSettingsVO(documentAppService.getDocumentSettings(userVO));
    }

    @Override
    public List<UploadResultVO> handleUpload(AuthenticatedUserVO user, List<DocumentUploadVO> uploads, UploadPurposeVO purpose)
            throws AccessDeniedException, DocumentGeneralException {
        List<UploadResult> upResults = documentAppService.handleUpload(user, user.getUsername(), uploads, purpose.asDocumentPurpose());
        List<UploadResultVO> result = new ArrayList<UploadResultVO>();
        for (UploadResult res : upResults) result.add(new UploadResultVO(res));
        return result;
    }

    @Override
    public void handleMailUpload( AuthenticatedUserVO user, String replyTo, String emailContent, List<UploadFilenameVO> attachmentFilenames) throws AccessDeniedException {
        documentAppService.handleMailUpload(user, replyTo, emailContent, attachmentFilenames);
    }

}
