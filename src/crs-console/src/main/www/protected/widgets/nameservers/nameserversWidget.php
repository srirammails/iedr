<?php

class nameserversWidget
    extends CWidget
    {

    /**
    * Model instance containing nameserver data
    *
    * @var    unknown
    * @access public
    */
    var $model = null;

    /**
    * Form instance that the widget is used in
    *
    * @var    unknown
    * @access public
    */
    var $form = null;

    /**
    * Name of variable in the model that contains nameserver array
    *
    * @var    string
    * @access public
    */
    var $nameservers = 'nameservers';

    /**
    * Name of variable in the model that contains IP address array
    * (if empty IP addresses will not be displayed)
    *
    * @var    string
    * @access public
    */
    var $ipAddresses = 'ipAddresses';

    /**
     * Name of variable in the model that contains number of nameservers
     *
     * @var    string
     * @access public
     */
    var $nameserversCount = 'nameserversCount';

    /**
     * Id of the field in the form in which the domain names are kept
     *
     * @var    string
     * @access public
     */
    var $domainId = 'domainname';

    /**
     * If true - domain names are kept in a listbox and may be selected partially
     * If false - there is a single domain name or they are kept as a string separated with commas
     *
     * @var    boolean
     * @access public
     */
    var $domainsAsArray = false;

    /**
    * If true - the widget defines its own rules to arrange items
    * If false - the general form rules are followed
    *
    * @var    boolean
    * @access public
    */
    var $self_arrange = true;

    /**
    * If true - item labels are displayed
    *
    * @var    boolean
    * @access public
    */
    var $labels = true;

    /**
    * If true - items are preceded by a piece of blank space on the left
    *
    * @var    boolean
    * @access public
    */
    var $indent = false;

    /**
    * Item size
    *
    * @var    integer
    * @access public
    */
    var $size;

    /**
    * If true - fields are disabled and the buttons are hidden
    *
    * @var    boolean
    * @access public
    */
    var $disabled = false;
    
    /**
     * If true - DNS Verification button is displayed
     *
     * @var    boolean
     * @access public
     */
    var $dnsCheck = true;

    public function run() {
        $this->render('application.widgets.nameservers.view',
                array(
                        'model' => $this->model,
                        'form' => $this->form,
                        'nameservers' => $this->nameservers,
                        'ipAddresses' => $this->ipAddresses,
                        'nameserversCount' => $this->nameserversCount,
                        'domainId' => $this->domainId,
                        'domainsAsArray' => $this->domainsAsArray,
                        'self_arrange' => $this->self_arrange,
                        'indent' => $this->indent,
                        'labels' => $this->labels,
                        'size' => $this->size,
                        'disabled' => $this->disabled,
                        'dnsCheck' => $this->dnsCheck,
                ));
    }

    }
?>