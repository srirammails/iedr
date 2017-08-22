<?php

/**
 * CSV File creation, for jqGrid
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
 * CSV file creation class
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
class CreateCSVFile
	{

    /**
     * csv-field separator - defaults to comma
     * @var    string
     * @access public
     */
	public $fldsep;
    /**
     * string-delimiter, default to double-quote
     * @var    string
     * @access public
     */
	public $strdelim;
    /**
     * line-terminating characters
     * @var    string
     * @access public
     */
	public $lnend;
    /**
     * filename to use in returned http header ; copied from constructor param
     * @var    string
     * @access public
     */
	public $filename;
    /**
     * column names string array ; copied from constructor param
     * @var    array
     * @access public
     */
	public $colheads;
    /**
     * array of arrays of strings to output in csv format ; copied from constructor param
     * @var    string
     * @access public
     */
	public $data;

    /**
     * constructor with parameters filename, column-names, data
     * 
     * Long description (if any) ...
     * 
     * @param  string $filename filename for http header
     * @param  array-of-string $colheads column names
     * @param  array-of-array-of-string $data     data to output in csv format
     * @return void   
     * @access public 
     */
	public function __construct($filename,$colheads,$data) {
		$this->strdelim = '"';
		$this->lnend = "\r\n";
		$this->fldsep = ',';
		$this->filename=$filename;
		$this->colheads=$colheads;
		$this->data=$data;
		}

    /**
     * outputs appropriate http headers for a csv file
     * 
     * @return void  
     * @access public
     */
	public function headers() {
		header('Pragma: public');
		header('Expires: 0');
		header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
		header('Content-Type: text/csv;charset=utf-8');
		header('Content-Disposition: inline; filename='.$this->filename.'.csv');
		}

    /**
     * Formats CSV data as a string, then returns it.
     * 
     * Formats CSV data as a string, then returns it, using a temporary file (see here: http://php.net/manual/en/wrappers.php.php)
     * 
     * @return string CSV data
     * @access public
     */
	public function output() {
		$csvdata = '';
		// open temp output file
		$out = fopen('php://temp', 'rwb');
		// output column headers
		$n = 0;
		foreach($this->colheads as $col) {
			$l = $this->strdelim.$col.$this->strdelim;
			if(++$n!=1) fputs($out, $this->fldsep);
			fputs($out, $l);
			}
		fputs($out, "\n"); // unix newline here, convert later
		// output data
		$buffer = '';
		foreach($this->data as $row)
			fputcsv($out, $row, $this->fldsep, $this->strdelim);
		// re-read output, replacing unix-newline with dos newline
		fseek($out, 0);
		while (($buffer = fgets($out, 4096)) !== false)
			$csvdata .= str_replace("\n","\r\n",$buffer);
		fclose($out);

		return $csvdata;
		}
	}

/**
 * Test function for creating CSV files
 * 
 * @return void
 */
function testCSVFile() {
	$heads = array('id','first','second','third');
	$data = array(
		array(1,'A','&euro; 17.345','C'),
		array(2,'D','E','2010-03-21'),
		array(3,'G','H','I'),
		);
	$p = new CreateCSVFile('test.xls',$heads,$data);
	$p->headers();
	echo $p->output();
	}

#testCSVFile();

