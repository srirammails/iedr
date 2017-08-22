<!-- START protected/widgets/jqGrid/view.php -->
<?php
/**
 * View file which outputs Html for a jqGrid widget
 * 
 * The JqGrid writes it's html onto the page at page load, into the content of two html tags:
 * 
 * - <table id="thisJqGrid">
 * 
 * - <div id="thisJqGridPager">
 * 
 * After the grid html has been written, the grid javascript then loads data from the supplied URL,
 * expecting it in JSON format.
 * The PHP below sets up the grid functions to correctly format the data, and also to tell the grid
 * about the columns (number, types, etc) and options (searchin/sorting etc).
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       view.php, jqGridWidget, jqGridComandButtonsWidget, GridModelBase
 */
# "$this" = jqGridWidget instance

$INFINITE_ROWS_CNT = 1000000;

// TODO: capture date filter as a date range
$datasource = isset($this->datasource) ? $this->datasource : "undefined";
$datatype = isset($this->datatype) ? $this->datatype : "json";
$datastr = isset($this->datastr) ? $this->datastr : "undefined";
$tableId = isset($this->tableId) ? $this->tableId : "thisJqGrid";
$navGrid = $this->navGrid ? 'true' : 'false'; 
$pager = $this->pager ? 'thisJqGridPager' : '';
$filterBar = $this->filterBar ? 'true' : 'false';

$thisfile = $this->thisfile;
$caption = $this->caption;
$numrows = $this->numrows;

$width = $this->width;
$do_sorting = $this->sorting;
$sortcolumnname = $this->model->defaultSortColumn;
$sortDirection = $this->model->defaultSortDirection;
$do_searching = $this->searching ? 'true' : 'false';
$getNicHandle = $this->get_nic_handle;
$selections = $this->selections;
$colNamesArr = '';
$colModelData = '';
Yii::log('model= ' .print_r($this->model,true));
if ($this->model != null) {
   // column-names array for jqGrid constructor
   $i = 1;
   $n = count($this->model->columns);
   foreach ($this->model->columns as $k => $v) {
      $l = $v['label'];
      $colNamesArr .= "'$l'";
      if ($i++ < $n)
         $colNamesArr .= ',';
   }
   // JqGrid column model Options : http://www.trirand.com/jqgridwiki/doku.php?id=wiki:colmodel_options
   foreach ($this->model->columns as $k => $v) {
     Yii::log('var:' . print_r($v, true));
      $cwidth = isset($v['width']) ? $v['width'] : 0;
      $search = 'search:' . (($do_searching and isset($v['criteriafield']) and ($v['criteriafield'] != null)) ? 'true' : 'false');
      if (isset($v['selectlist'])) {
         $search .= ', stype: "select", sopt: "eq", searchoptions: { value:"' . $v['selectlist'] . '" }';
      }
      if (isset($v['sorting'])) {
          $do_sorting = $v['sorting'];
      }
      $sort = 'sortable:' . ($do_sorting ? 'true, sorttype:"' . (isset($v['type']) ? $v['type'] : 'string') . '"' : 'false');
      $format = '';
      if (isset($v['link'])) {
         if (isset($v['resultfield']) && $v['link']) {
               $format .= ', formatter:linkFormatter, formatoptions:{addParam:"' . $v['resultfield'] . '",  baseLinkUrl:"' . $v['link'] . '"}';
         }
      }
      if (isset($v['type'])) {
         switch ($v['type']) {
            // see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:predefined_formatter#predefined_format_types
            case 'datefilter':
               $sort = ' ';
               $format .= ' sortable:"true", sorttype:"string", stype:"date", editable: true, searchoptions:{
                             dataInit:function(elem){
                                jQuery(elem).datepicker(
                                {
                                    dateFormat:"yy-mm-dd",
                                    changeMonth: true,
                                    changeYear:true,
                                    showOn: "both",
                                    buttonImageOnly: true,
                                    buttonImage: "images/calendar.gif",
                                    altFormat: "yy-mm-dd",
                                    buttonText: "Get date",
                                    onSelect: function (dateText, inst) { $("#' . $tableId . '")[0].triggerToolbar();},
                                    showButtonPanel: true,
                                    closeText: "Clear",
                                    beforeShow: function(input) {
                                                    setTimeout(
                                                        function() {
                                                        var clearButton = $(input).datepicker("widget").find(".ui-datepicker-close");
                                                        clearButton.unbind("click").bind("click", function(){$.datepicker._clearDate(input);});
                                                    }, 1);
                                                }
                                });
                             }
					        } ';
               break;
             case 'currency':
               $format .= ', formatter:currencyDecorator';
               break;
            case 'email':
               $format .= ', formatter:emailFormatter';
               break;
            case 'hidden':
               $format .= ', hidden:true';
               break;
            case 'disabledStatus':
               $format .= ', formatter:disabledStatusFormatter';
               break;
            case 'externalRecipients':
               	$format .= ', formatter:externalRecipientsFormatter';
               	break;
            case 'eID':
                $format .= ', formatter:eIdFormatter';
                break;
         }
      }

      $cTitle = isset($v['title']) ? $v['title'] : 'true';

      if ($colModelData != '')
         $colModelData .= ",";

      $colModelData .= BR . "{ name:'$k', index:'$k', width:$cwidth, $search, $sort $format, title:$cTitle }";
      Yii::log('' . print_r($colModelData, true), 'debug', 'MODEL_DATA');
   }
}
?>
<script type="text/javascript">
   function setCookie(k,v,expdays)
   {
      var exdate=new Date();
      exdate.setDate(exdate.getDate() + (expdays===null?3:expdays));
      document.cookie = k+"="+escape(v)+"; expires="+exdate.toUTCString();
   }
   
   function getCookie(k)
   {
      var i,x,y,ARRcookies=document.cookie.split(";");
      for (i=0;i<ARRcookies.length;i++)
      {
         x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
         y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
         x=x.replace(/^\s+|\s+$/g,"");
         if (x===k)
         {
            return unescape(y);
         }
      }
   }
   
   function emailFormatter(e) {
      return '<a href="mailto:'+e+'">'+e+'</a>';
   }

   function currencyDecorator(num) {
      /* render a number with currency symbol  */
      csym = "&euro;";
      return (csym+' '+num);
   }

   function linkFormatter(cellvalue, options, rowObject)
   {
      var link =  options.colModel.formatoptions.baseLinkUrl+'&id='+cellvalue;
      return '<a href="index.php?r='+ link +'">'+cellvalue+'</a>';
   }

   function eIdFormatter(e) {
      return '<center>'+e+'</center>';
   }

   function externalRecipientsFormatter(cellvalue, options, rowObject) {
        to = '';
        cc = '';
        bcc = '';
        if (cellvalue.to != null) {
            to =  cellvalue.to;
        };
        if (cellvalue.cc != null) {
            cc =  cellvalue.cc;
        };
        if (cellvalue.bcc != null) {
            bcc =  cellvalue.bcc;
        };
        result = 'to:'+ to +'<br\>'+ 'cc:'+ cc+'<br\>'+ 'bcc:'+ bcc;
        return result;
  }

   function disabledStatusFormatter(cellvalue, options, rowObject) {
        if (cellvalue.suppressible == 1) {
            var name = 'radio_' + options.rowId + '_' + options.colModel.name;
            var checkedForEnabled = cellvalue.disabled == 0 ? 'checked="true"' : '';
            var checkedForDisabled = cellvalue.disabled == 1 ? 'checked="true"' : '';
            var onclickForEnabled = 'onclick="enableEmail(' + cellvalue.eId + ');"';
            var onclickForDisabled = 'onclick="disableEmail(' + cellvalue.eId + ');"';
            return '<center>' 
                + '<input type="radio" name="' + name + '"value="enabled" ' + checkedForEnabled + ' ' + onclickForEnabled + '/>&nbsp;On&nbsp;'
                + '<input type="radio" name="' + name + '"value="disabled" ' + checkedForDisabled + ' ' + onclickForDisabled + '/>&nbsp;Off&nbsp;'
                + '</center>';
       } else {
           result = '<center>' ;
           result += 'Cannot be disabled';
           result += cellvalue.nonSuppressibleReason != null ? ': ' + cellvalue.nonSuppressibleReason  + '.': '.';
           result += '</center>' ;
           return result;
       }
   }

   function checkBoxClicked(s)
   {
      if(window.handleCheckBoxClicked) { handleCheckBoxClicked(s); }
   }

   function getNumGridRows()
   {
       var numrows = <?php echo $numrows; ?>;
       if ("<?php echo $navGrid; ?>" == "true"){
           var cookienumrows = getCookie("numrows");
           if(cookienumrows != null && cookienumrows>0)
               numrows = cookienumrows;
               setCookie("numrows",numrows,3);
       }
       return numrows;
   }
</script>

<?php /* these next table and div tags are rewritten by jqGrid, using jquery functions, after page-load. */ ?>


<!--  <script type="text/javascript">
  jQuery(document).ready(function() {
    jQuery("#gs_F").datepicker({changeYear:true,dateFormat: 'yy-mm-dd'});
    jQuery(".ui-datepicker").css({'font-size':'80%'});
  });
  </script>-->

<div id="how_select"></div>  
<table summary="grid" id="<?php echo $tableId; ?>"></table>
<div id="thisJqGridPager"></div>


<script type="text/javascript" >

   function extractLinkText(t) {
      var rx = new RegExp(' *<[^>]*> *', "g");
      return t.replace(rx, '');
   }

   function index_handleSelectedRow(rowIndex) {
      var rowDataArr = jQuery("#<?php echo $tableId; ?>").getRowData();
      var dom = rowDataArr[rowIndex].<?php echo $sortcolumnname; ?>;
      handleSelectedRow(extractLinkText(dom));
   }
   
   function tagsRemove(text){
      var tags = [];
      var found = '';
      var stringToReplace = '';

      while(true) {
         found = text.indexOf('<');
         if(-1 == found)
            break;
         tags.push(found);

         found = text.indexOf('>');
         if(-1 == found)
            break;
         tags.push(found+1);
									
         stringToReplace = text.substring(tags[0],tags[1]);
         text = text.replace(stringToReplace,'');

         while(tags.length)
            tags.pop();
      }
      return text;
   }
   
   function getNicHandle(rowid, colid) {
      var current = jQuery("#<?php echo $tableId; ?>").getCell(rowid, colid);
      var nicElem = document.getElementById('NicSearchModel_returningData_nic');
      if(nicElem != null) {
         nicElem.value = tagsRemove(current);
         var elem = document.getElementsByName('yt0');
         elem[0].click();
      }
   }

   function checkSession(){
       $.ajax({
		    url:'<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=site/issesion'; echo $host;?>',
		    success: function(str) { if(str == 'errors') {  alert('Service session timeout.');window.location = '<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=site/logout'; echo $host;?>'; }; }
		});
   }

   function getDataUrl(appendStr) {
      return "<?php echo $datasource; ?>" + appendStr;
   }

   var idsOfSelectedRows = [];
   function updateIdsOfSelectedRows(id, isSelected) {
       var limit = <?php echo Yii::app()->params['bulk_operation_limit'] ?>;
       var index = $.inArray(id, idsOfSelectedRows);
       if (!isSelected && index >= 0) {
           idsOfSelectedRows.splice(index, 1);
       } else if (isSelected && index < 0 && idsOfSelectedRows.length < limit) {
           idsOfSelectedRows.push(id);
       }
   }

   function updateAllIdsOnPage(aRowIds, isSelected) {
       var i, count, id;
       for (i = 0, count = aRowIds.length; i < count; i++) {
           id = aRowIds[i];
           updateIdsOfSelectedRows(id, isSelected);
       }
   }

   //datepicker _gotoToday overrided to select current date
   var old_goToToday = $.datepicker._gotoToday
   $.datepicker._gotoToday = function(id) {
       old_goToToday.call(this,id)
       this._selectDate(id)
   }

   $(document).ready(function() {
      jQuery('#<?php echo $tableId; ?>').jqGrid({
         hideGrid: false,
         hoverrows: true,
         rowList:[5,10,15,20,25,50,100,250<?php if($allowAll) {
             echo "," . $INFINITE_ROWS_CNT;
         } ?>],
         pager: "<?php echo $pager; ?>",
         caption: "<?php echo $caption; ?>",
         viewrecords: true,
         onPaging: function(pgButton) {
            if(pgButton==='records') { 
               var n = jQuery(".ui-pg-selbox option:selected").val();
               setCookie('numrows',n,3); 
            };
         },
         onSelectAll: 
         <?php 
               if($selections) {
                  echo "function(rowIdxArray, sts) {
                           updateAllIdsOnPage(rowIdxArray, sts)
                           if(window.handleSelectedRow) {
                              jQuery(rowIdxArray).each( 
                                 function(rowIndex) { 
                                    index_handleSelectedRow(rowIndex);
                              });
                           }
                        }";
               } else {
                  echo "function(rowIdxArray, sts) {}";
               }
         ?>,
         onSelectRow:
         <?php
            if($selections) {
               if($getNicHandle) {
                  echo "function(rowid) {
                           getNicHandle(rowid,2);
                           if(window.handleSelectedRow) {
                              handleSelectedRow(rowid);
                              updateIdsOfSelectedRows(rowid, isSelected);
                           }
                        }";
               } else {
                  echo "function(rowid, isSelected) {
                           if(window.handleSelectedRow) {
                              handleSelectedRow(rowid);
                              updateIdsOfSelectedRows(rowid, isSelected);
                           }
                        }";
               }
            } else {
                if($getNicHandle) {
                   echo "function(rowid) {
                      getNicHandle(rowid,1);
                   }";
                } else {
                   echo "function(rowid) { }";
                }
            }
         ?>,
         multiselect:
         <?php 
            if(!$selections)
               echo "false";
            else 
               echo "true"; 
         ?>,
         loadComplete: function () {
             var $this = $(this), i, count;
             for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                 $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
             }
             <?php
                 if($selections) {
                     echo "handlePageLoading(jQuery('#" . $tableId . "').jqGrid('getDataIDs'));";
                 }
             ?>
         },
         beforeRequest: function(rowid){ checkSession(); },
         //datatype: 'json',
         datatype: "<?php echo $datatype; ?>",
         colNames: [ <?php echo $colNamesArr; ?> ],
         colModel: [ <?php echo $colModelData; ?> ],
         url: getDataUrl(''),
         datastr: '<?php echo $datastr; ?>',
         width: "<?php echo $width; ?>", height: "100%",
         postData: { "oper":"grid" },
         toolbar: [true,"top"],
         rownumbers: true,
         rowNum:getNumGridRows(),
         sortname: "<?php echo $sortcolumnname; ?>",
         sortorder: "<?php echo $sortDirection; ?>"
      });

      $("#<?php echo $pager; ?> option[value=<?php echo $INFINITE_ROWS_CNT; ?>]").text('All');
      if("<?php echo $filterBar; ?>" == "true"){
        jQuery('#<?php echo $tableId; ?>').jqGrid('filterToolbar', { stringResult:true , searchOnEnter : false});
      }
      if ("<?php echo $navGrid; ?>" == "true"){
        jQuery("#<?php echo $tableId; ?>").jqGrid("navGrid","#<?php echo $pager; ?>",
        { /* pager options */
          add:false, del:false, edit:false,
          search: <?php echo $do_searching; ?>,
          refresh:true,
          csv:true, excel:true,
          position:'left'
        },
        { /*edit options*/},
        { /*add options*/},
        { /*del options*/},
        { /*search dialog-box options*/
          drag:true,
          resize:true,
          closeOnEscape:true,
          dataheight:150,
          datawidth:450,
          closeAfterSearch:true,
          multipleSearch:true
        });

      /* Toolbar Export-As-Excel Button */
        jQuery('#<?php echo $tableId; ?>').jqGrid('navButtonAdd', '#<?php echo $pager; ?>', {
          caption: '', title: 'Export To Excel',
          onClickButton: function (e) {
            try { jQuery('#<?php echo $tableId; ?>').jqGrid('excelExport', { tag: 'excel', url: getDataUrl('&oper=excel') }); }
            catch (ex1) { window.location = '<?php echo $thisfile; ?>'; }
          },
          buttonicon: 'ui-icon-newwin'
        });

      /* Toolbar Export-As-CSV Button */
        jQuery('#<?php echo $tableId; ?>').jqGrid('navButtonAdd', '#<?php echo $pager; ?>',{
          caption:'', title:'Export To CSV',
          onClickButton : function(e) {
            try { jQuery("#<?php echo $tableId; ?>").jqGrid('excelExport',{tag:'csv', url: getDataUrl('&oper=csv') }); }
            catch (ex2) { window.location= '<?php echo $thisfile; ?>'; }
          },
          buttonicon:'ui-icon-document'
        });
      }
      $.ajaxSetup ({ cache: true });
   });

</script>
<!-- END protected/widgets/jqGrid/view.php -->