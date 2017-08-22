<?php

/**
 * Short description for file
 *
 * Long description (if any) ...
 *
 * PHP version 5
 *
 * New Registration Console (C) IEDR 2011
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */

/**
 * Short description for class
 *
 * Long description (if any) ...
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
class ResourcesController extends DummyController
{

    /**
     * Description for public
     * @var    array
     * @access public
     */
    public $breadcrumbs = array('Resources');

    private $files = array(
        'account_pay_check_deposit.xml' => 'files/api/xml/1.1/account_pay_check_deposit.xml',
        'account_pay_check_deposit_response.xml' => 'files/api/xml/1.1/account_pay_check_deposit_response.xml',
        'account_pay_deposit_funds.xml' => 'files/api/xml/1.1/account_pay_deposit_funds.xml',
        'account_pay_deposit_funds_response.xml' => 'files/api/xml/1.1/account_pay_deposit_funds_response.xml',
        'account_pay_from_deposit.xml' => 'files/api/xml/1.1/account_pay_from_deposit.xml',
        'account_pay_from_deposit_response.xml' => 'files/api/xml/1.1/account_pay_from_deposit_response.xml',
        'account_pay_msdReActivation.xml' => 'files/api/xml/1.1/account_pay_msdReActivation.xml',
        'account_pay_msdReActivation_payFromDeposit.xml' => 'files/api/xml/1.1/account_pay_msdReActivation_payFromDeposit.xml',
        'account_pay_msdReActivation_response.xml' => 'files/api/xml/1.1/account_pay_msdReActivation_response.xml',
        'account_pay_offline.xml' => 'files/api/xml/1.1/account_pay_offline.xml',
        'account_pay_offline_response.xml' => 'files/api/xml/1.1/account_pay_offline_response.xml',
        'account_pay_online.xml' => 'files/api/xml/1.1/account_pay_online.xml',
        'account_pay_online_response.xml' => 'files/api/xml/1.1/account_pay_online_response.xml',
        'account_query_curr_ren_reg.xml' => 'files/api/xml/1.1/account_query_curr_ren_reg.xml',
        'account_query_curr_ren_reg_response.xml' => 'files/api/xml/1.1/account_query_curr_ren_reg_response.xml',
        'account_query_fut_ren.xml' => 'files/api/xml/1.1/account_query_fut_ren.xml',
        'account_query_fut_ren_response.xml' => 'files/api/xml/1.1/account_query_fut_ren_response.xml',
        'account_query_msd.xml' => 'files/api/xml/1.1/account_query_msd.xml',
        'account_query_msd_response.xml' => 'files/api/xml/1.1/account_query_msd_response.xml',
        'account_query_tran.xml' => 'files/api/xml/1.1/account_query_tran.xml',
        'account_query_tran_adv.xml' => 'files/api/xml/1.1/account_query_tran_adv.xml',
        'account_query_tran_adv_response.xml' => 'files/api/xml/1.1/account_query_tran_adv_response.xml',
        'account_query_tran_response.xml' => 'files/api/xml/1.1/account_query_tran_response.xml',
        'contact_create.xml' => 'files/api/xml/1.1/contact_create.xml',
        'contact_create_response.xml' => 'files/api/xml/1.1/contact_create_response.xml',
        'contact_info.xml' => 'files/api/xml/1.1/contact_info.xml',
        'contact_info_response.xml' => 'files/api/xml/1.1/contact_info_response.xml',
        'contact_modify.xml' => 'files/api/xml/1.1/contact_modify.xml',
        'contact_modify_response.xml' => 'files/api/xml/1.1/contact_modify_response.xml',
        'contact_query_admin.xml' => 'files/api/xml/1.1/contact_query_admin.xml',
        'contact_query_admin_response.xml' => 'files/api/xml/1.1/contact_query_admin_response.xml',
        'contact_query_all.xml' => 'files/api/xml/1.1/contact_query_all.xml',
        'contact_query_all_response.xml' => 'files/api/xml/1.1/contact_query_all_response.xml',
        'contact_query_tech.xml' => 'files/api/xml/1.1/contact_query_tech.xml',
        'contact_query_tech_response.xml' => 'files/api/xml/1.1/contact_query_tech_response.xml',
        'domain_autocreate.xml' => 'files/api/xml/1.1/domain_autocreate.xml',
        'domain_autocreate_response.xml' => 'files/api/xml/1.1/domain_autocreate_response.xml',
        'domain_check.xml' => 'files/api/xml/1.1/domain_check.xml',
        'domain_check_response.xml' => 'files/api/xml/1.1/domain_check_response.xml',
        'domain_create.xml' => 'files/api/xml/1.1/domain_create.xml',
        'domain_create_response.xml' => 'files/api/xml/1.1/domain_create_response.xml',
        'domain_delete.xml' => 'files/api/xml/1.1/domain_delete.xml',
        'domain_delete_response.xml' => 'files/api/xml/1.1/domain_delete_response.xml',
        'domain_info.xml' => 'files/api/xml/1.1/domain_info.xml',
        'domain_info_response.xml' => 'files/api/xml/1.1/domain_info_response.xml',
        'domain_modify.xml' => 'files/api/xml/1.1/domain_modify.xml',
        'domain_modify_autorenew.xml' => 'files/api/xml/1.1/domain_modify_autorenew.xml',
        'domain_modify_autorenew_response.xml' => 'files/api/xml/1.1/domain_modify_autorenew_response.xml',
        'domain_modify_response.xml' => 'files/api/xml/1.1/domain_modify_response.xml',
        'domain_msd.xml' => 'files/api/xml/1.1/domain_msd.xml',
        'domain_msd_response.xml' => 'files/api/xml/1.1/domain_msd_response.xml',
        'domain_msdcancel.xml' => 'files/api/xml/1.1/domain_msdcancel.xml',
        'domain_msdcancel_response.xml' => 'files/api/xml/1.1/domain_msdcancel_response.xml',
        'domain_query_all.xml' => 'files/api/xml/1.1/domain_query_all.xml',
        'domain_query_all_contact.xml' => 'files/api/xml/1.1/domain_query_all_contact.xml',
        'domain_query_all_contact_contacttype.xml' => 'files/api/xml/1.1/domain_query_all_contact_contacttype.xml',
        'domain_query_all_contact_contacttype_response.xml' => 'files/api/xml/1.1/domain_query_all_contact_contacttype_response.xml',
        'domain_query_all_contact_response.xml' => 'files/api/xml/1.1/domain_query_all_contact_response.xml',
        'domain_query_all_response.xml' => 'files/api/xml/1.1/domain_query_all_response.xml',
        'domain_query_transfer.xml' => 'files/api/xml/1.1/domain_query_transfer.xml',
        'domain_query_transfer_response.xml' => 'files/api/xml/1.1/domain_query_transfer_response.xml',
        'domain_transfer.xml' => 'files/api/xml/1.1/domain_transfer.xml',
        'domain_transfer_response.xml' => 'files/api/xml/1.1/domain_transfer_response.xml',
        'domain_transfer_using_defaults.xml' => 'files/api/xml/1.1/domain_transfer_using_defaults.xml',
        'login.xml' => 'files/api/xml/1.1/login.xml',
        'login_response.xml' => 'files/api/xml/1.1/login_response.xml',
        'logout.xml' => 'files/api/xml/1.1/logout.xml',
        'logout_response.xml' => 'files/api/xml/1.1/logout_response.xml',
        'ticket_delete.xml' => 'files/api/xml/1.1/ticket_delete.xml',
        'ticket_delete_response.xml' => 'files/api/xml/1.1/ticket_delete_response.xml',
        'ticket_info.xml' => 'files/api/xml/1.1/ticket_info.xml',
        'ticket_info_response.xml' => 'files/api/xml/1.1/ticket_info_response.xml',
        'ticket_modify.xml' => 'files/api/xml/1.1/ticket_modify.xml',
        'ticket_modify_response.xml' => 'files/api/xml/1.1/ticket_modify_response.xml',
        'ticket_modify_status.xml' => 'files/api/xml/1.1/ticket_modify_status.xml',
        'ticket_query_all.xml' => 'files/api/xml/1.1/ticket_query_all.xml',
        'ticket_query_all_response.xml' => 'files/api/xml/1.1/ticket_query_all_response.xml',
        'ticket_query_deletions.xml' => 'files/api/xml/1.1/ticket_query_deletions.xml',
        'ticket_query_deletions_response.xml' => 'files/api/xml/1.1/ticket_query_deletions_response.xml',
        'ticket_query_modifications.xml' => 'files/api/xml/1.1/ticket_query_modifications.xml',
        'ticket_query_modifications_response.xml' => 'files/api/xml/1.1/ticket_query_modifications_response.xml',
        'ticket_query_registrations.xml' => 'files/api/xml/1.1/ticket_query_registrations.xml',
        'ticket_query_registrations_response.xml' => 'files/api/xml/1.1/ticket_query_registrations_response.xml',

        'xml-examples_v1.1.9.tar' => 'files/api/xml/1.1/xml-examples_v1.1.9.tar',
        'connectivity.pdf' => 'files/api/docs/connectivity.pdf',
        'IEDR-API.XML-Interface_v1.1.9.pdf' => 'files/api/docs/IEDR-API.XML-Interface_v1.1.9.pdf',
        'IEAPI-Response-Codes-v1.1.9.pdf' => 'files/api/docs/IEAPI-Response-Codes-v1.1.9.pdf',
        'api-client_v1.1.10.tgz' => 'files/api/src/api-client_v1.1.10.tgz',
        'Changelog.v1.1.9.txt' => 'files/api/docs/Changelog.v1.1.9.txt',

        'ie-ROOT-CA.pem' => 'files/api/ssl/ie-ROOT-CA.pem',
        'ie-testing-CA.pem' => 'files/api/ssl/ie-testing-CA.pem',
        'openssl.production.txt' => 'files/api/ssl/openssl.production.txt',
        'openssl.testing.txt' => 'files/api/ssl/openssl.testing.txt',

        'IEDR-Registrar-Agreement-draft-160309-v2.pdf' => 'files/docs/IEDR-Registrar-Agreement-draft-160309-v2.pdf',
        'IEDR_Registrar_Agreement_-_draft_160209_v1.pdf' => 'files/docs/IEDR_Registrar_Agreement_-_draft_160209_v1.pdf',
        'IEDR_Policy_Advisory_Committee_PAC_ToR_-_draft_160209_v1.pdf' => 'files/docs/IEDR_Policy_Advisory_Committee_PAC_ToR_-_draft_160209_v1.pdf',
        'IEDR_Privacy_Statement_-_draft_160209_v1.pdf' => 'files/docs/IEDR_Privacy_Statement_-_draft_160209_v1.pdf',
        'IEDR_Appeals_process-draft-230209-v1.pdf' => 'files/docs/IEDR_Appeals_process-draft-230209-v1.pdf',
        'IEDR-Complaints-Procedure-draft-230209-v1.pdf' => 'files/docs/IEDR-Complaints-Procedure-draft-230209-v1.pdf',
        'appeals_policy_formation.pdf' => 'files/docs/appeals_policy_formation.pdf',
        'IEDRs_LIC_Consultations.pdf' => 'files/docs/IEDRs_LIC_Consultations.pdf',
        'Pending_RBN_clarification.pdf' => 'files/docs/Pending_RBN_clarification.pdf',
        'multi_year_reg_faq.pdf' => 'files/docs/multi_year_reg_faq.pdf',
        'IEDR_logo_guidelines.pdf' => 'files/docs/IEDR_logo_guidelines.pdf',
        'IE_Accredited_Registrar_Logo_guidelines.pdf' => 'files/docs/IE_Accredited_Registrar_Logo_guidelines.pdf',
        'Top20Resellers2008.pdf' => 'files/docs/Top20Resellers2008.pdf',
        'Top20Registrars20100806.pdf' => 'files/docs/Top20Registrars20100806.pdf',
        '' => 'files/docs/',

        'IEDR10_logo2.eps' => 'files/IEDR_Logo_Guidelines/IEDR10_logo2.eps',
        'IEDR10_logo2.jpg' => 'files/IEDR_Logo_Guidelines/IEDR10_logo2.jpg',
        'IEDR_col_stamp.png' => 'files/IEDR_Logo_Guidelines/IEDR_col_stamp.png',
        'IEDR_col_stamp_txt.png' => 'files/IEDR_Logo_Guidelines/IEDR_col_stamp_txt.png',
        'IEDR_col_stamp_txt_white_background.png' => 'files/IEDR_Logo_Guidelines/IEDR_col_stamp_txt_white_background.png',
        'IEDR_white_stamp.png' => 'files/IEDR_Logo_Guidelines/IEDR_white_stamp.png',
        'IEDR_white_stamp_txt.png' => 'files/IEDR_Logo_Guidelines/IEDR_white_stamp_txt.png',

    );

    public function actionDownload()
    {
        //Yii::log("QQQ".print_r($_GET, true));
        if (sizeof($_GET) > 0) {

            foreach ($_GET as $id_ => $title_) {
                $i = 0;
                $name = $id_;
                if ($i == 1) {
                    break;
                }
                $i++;
            }

            //Yii::log("QQQ".print_r($name, true));
            $fileName = $this->files[$name];
            $this->setContentType($name);
            header('Content-Description: File Transfer');
            header('Content-Disposition: attachment; filename='.basename($fileName));
            header('Content-Transfer-Encoding: binary');
            header('Expires: 0');
            header('Cache-Control: must-revalidate');
            header('Pragma: public');
            header('Content-Length: ' . filesize($fileName));
            ob_clean();
            flush();
            readfile($fileName);
            exit;
        }
    }

    private function  setContentType($name) {
        if (strpos($name, '.xml')) {
            header('Content-Type: text/xml');
            return;
        }
        if (strpos($name, '.pdf')) {
            header('Content-Type: application/pdf');
            return;
        }
        if (strpos($name, '.txt')) {
            header('Content-Type: text/plain');
            return;
        }
        header('Content-Type: application/octet-stream');
    }

}

