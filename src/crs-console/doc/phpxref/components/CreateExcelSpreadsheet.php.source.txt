<?php

/**
 * Excel File creation, for jqGrid
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
 * Excel file creation class
 * 
 * derived from:
 * 	Easy way to create XLS file from PHP
 * 	Posted on Wednesday, October 11 @ 09:52:55 ICT by apples
 * 	http://www.appservnetwork.com/modules.php?name=News&file=article&sid=8
 * see also
 * 	http://sc.openoffice.org/excelfileformat.pdf
 * 	http://www.zedwood.com/article/133/generate-xls-spreadsheet-files-with-php
 * 	http://pear.php.net/package/Spreadsheet_Excel_Writer
 * 	http://www.the-art-of-web.com/php/dataexport/
 * 	http://support.microsoft.com/kb/214233
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
class CreateExcelSpreadsheet
	{

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
		$this->filename=$filename;
		$this->colheads=$colheads;
		$this->data=$data;
		}

    /**
     * outputs appropriate http headers for an Excel file
     * 
     * @return void  
     * @access public
     */
	public function headers() {
		header('Pragma: public');
		header('Expires: 0');
		header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
		header('Content-Type: application/vnd.ms-excel');
		header('Content-Disposition: attachment;filename='.$this->filename.'.xls');
		header('Content-Transfer-Encoding: binary');
		}

    /**
     * returns Excel file
     * 
     * @return string binary excel file data
     * @access public
     */
	public function output() {
		$out = $this->xlsBOF();
		$row = 0; $col = 0;
		foreach($this->colheads as $hdrdata) {
			$out.=$this->xlsWriteLabel($row,$col++,$hdrdata);
			}
		foreach($this->data as $rowarr) {
			++$row; $col=0;
			foreach($rowarr as $celldata) {
				$out.=$this->xlsWriteLabel($row,$col++,$celldata);
				}
			}
		$out.=$this->xlsEOF();
		return $out;
		}

    /**
     * returns BIFF5 header record
     * 
     * @return mixed     BIFF5 header record
     * @access protected
     */
	protected function xlsBOF() {
		return pack('ssssss', 0x809, 0x8, 0x0, 0x10, 0x0, 0x0);
		}

    /**
     * returns BIFF5 EOF
     * 
     * @return mixed     BIFF5 EOF
     * @access protected
     */
	protected function xlsEOF() {
		return pack('ss', 0x0A, 0x00);
		}

    /**
     * returns BIFF5 formatted number
     * 
     * @param  integer   $Row   row number
     * @param  integer   $Col   column number
     * @param  number    $Value cell value
     * @return string    Return BIFF5 formatted number
     * @access protected
     */
	protected function xlsWriteNumber($Row, $Col, $Value) {
		return pack('sssss', 0x203, 14, $Row, $Col, 0x0).pack('d', $Value);
		}

    /**
     * returns BIFF5 formatted string
     * 
     * @param  integer   $Row   row number
     * @param  integer   $Col   column number
     * @param  string    $Value cell value
     * @return string    BIFF5 formatted string
     * @access protected
     */
	protected function xlsWriteLabel($Row, $Col, $Value ) {
		$L = strlen($Value);
		return pack('ssssss', 0x204, 8 + $L, $Row, $Col, 0x0, $L).$Value;
		} 
	}

/**
 * Test function for creating Excel files
 * 
 * @return void
 */
function testExcelSpreadsheet() {
	$heads = array('id','first','second','third');
	$data = array(
		array(1,'A','&euro; 17.345','C'),
		array(2,'D','E','2010-03-21'),
		array(3,'G','H','I'),
		);
	$p = new CreateExcelSpreadsheet('test.xls',$heads,$data);
	$p->headers();
	echo $p->output();
	}

#testExcelSpreadsheet();

