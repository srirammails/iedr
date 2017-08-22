<style>
#documents_list {
    list-style: none outside none;
    padding: 0px 20px;
}
.document_upload a {
    float: right;
}
.document_upload {
    border-bottom: 1px solid #888888;
    padding: 16px 0;
}
.file_name {
    font-size: 1.1em;
}
.file_metadata {
    margin: 6px 0 10px;
}
.metadata_header {
    color: #888888;
    font-size: 85%;
    line-height: 1.6em;
    font-weight: bold;
    letter-spacing: 0.1em;
    text-transform: uppercase;
}
.metadata_value {
    color: #666666;
    font-size: 93.5%;
    font-weight: bold;
    letter-spacing: 0;
    margin-right: 10px;
    padding: 0 4px;
    text-transform: uppercase;
    position: relative;
}
.metadata_value span {
    position: absolute;
    padding: 4px;
    top: 20px;
    left: -10px;
    width: 200px;
    font-weight: normal;
    text-transform: none;
    background: #FFF;
    border-radius: 3px;
    box-shadow: 0px 1px 4px #676;
}
.file_assign .file_assign_details > label {
    display: block;
    float: left;
    font-weight: normal;
    line-height: 2em;
    width: 25%;
}
.file_assign_details {
    overflow: auto;
    padding-top: 4px;
}
.file_assign_details input {
    margin-top: 3px;
    vertical-align: middle;
}
.upload_next input[type=file] {
    margin: 10px 0;
}
.requirements_list {
    list-style: none outside none;
    overflow: auto;
    padding: 0;
}
.allowed_filetypes {
    list-style: none outside none;
    padding: 0;
}
.allowed_filetypes li {
    color: #444444;
    float: left;
    font-size: 85%;
    font-weight: bold;
    line-height: 16px;
    padding: 0 5px;
    text-transform: uppercase;
}
.requirements_list > li {
    margin-top: 5px;
}
</style>

<script id="documentUploadTemplate" type="text/template">
<li class="document_upload">
    <div class="upload_next">
        <div>You can upload <span databind="documents_left_counter"/> more documents. Select next document to upload</div>
        <input type="file" name="documents[]">
        <div>
            <span class="metadata_header">Requirements:</span>
            <ul class="requirements_list">
                <li>Document must be smaller than <span class="metadata_value allowed_filesize"/></li>
                <li><span style="float: left;">Allowed filetypes:</span><ul class="allowed_filetypes">
                </ul>
                </li>
            </ul>
        </div>
    </div>
    <div class="uploaded_file_details">
        <div class="file_header">
            <a class="remove_upload" href="#">Remove</a>
            <div class="selected_file">
                <span class="file_name"><span databind="file_name"/></span>
                <div class="file_metadata">
                    <span class="metadata_header">Size:</span><span class="metadata_value" databind="file_size"/>
                    <span class="metadata_header">Type:</span><span class="metadata_value" databind="file_type"/>
                </div>
            </div>
        </div>
        <?php if ($multipleDomains): ?>
        <div class="file_assign">
            <a class="assign_edit" href="#">Edit</a>
            <span class="metadata_header">Domains:</span>
            <span class="file_assign_summary"><em databind="file_name"/> is assigned to <span databind="domain_count_description"/></span>
            <div class="file_assign_details"/>
        </div>
        <?php endif; ?>
    </div>
</li>
</script>

<script type="text/javascript">
$(function() {
    var MAX_DOCUMENT_COUNTER = <?php echo $uploader->getDocumentCountLimit(); ?>;
    var MAX_DOCUMENT_SIZE = <?php echo $uploader->getDocumentSizeLimit(); ?>;
    var ALLOWED_TYPES = $.grep(<?php echo '"'.implode(',', $uploader->getAllowedTypes()).'"'; ?>.split(','), function(e){ return e.toLowerCase(); });
    var DOMAINS = $.grep(<?php echo '"'.implode(',', $domains).'"'; ?>.split(','), function(e){ return e.toLowerCase(); });
    var UPLOADER_TPL = $("#documentUploadTemplate").text();

    var document_counter = function() {
        var counter = 0;
        function notifyDataChange() {
            var ctx = $("#documents_list").parent();
            var left_counter = MAX_DOCUMENT_COUNTER - counter;
            $("[databind=documents_left_counter]", ctx).text(left_counter);
            $("[databind=documents_counter]", ctx).text(counter);
            $("[databind=documents_count_limit]", ctx).text(MAX_DOCUMENT_COUNTER);
        }
        return {
            get : function() { return counter; },
            set : function(new_counter) {
                counter = new_counter;
                notifyDataChange();
            },
            inc : function() { this.set(counter + 1); },
            dec : function() { this.set(counter - 1); },
            sync : function() { notifyDataChange(); }
        }
    }();

    function humanize_size(size_in_bytes) {
        if (size_in_bytes >= 1<<20)
            return (size_in_bytes / (1<<20)).toFixed(2) + " Mb";
        else if (size_in_bytes >= 1<<10)
            return Math.round(size_in_bytes / (1<<10)) + " Kb";
        else
            return size_in_bytes + " bytes";

    }
    function notifyUploadDataChange(ctx, name, size, type) {
        $('[databind=file_name]', ctx).text(name);
        $('[databind=file_type]', ctx).text(type);

        if (size > 0)
            $('[databind=file_size]', ctx).text(humanize_size(size));
        else {
            // size unknown, add special tooltip and hover handler
            var tooltip = $("<span\>").text("This browser does not support checking file size before upload").hide();
            $('[databind=file_size]', ctx).text("???").append(tooltip).hover(function() { tooltip.toggle(); });
        }
    }
    function type_from_extension(extension) {
        var type = extension.toLowerCase();
        return type;
    }
    function fileInputValueToName(inputValue) {
        return inputValue.split('\\').pop();
    }
    function isValid($elem, name, size, type) {
        if (size > MAX_DOCUMENT_SIZE) {
            alert("File size exceeds maximum allowed size of " + humanize_size(MAX_DOCUMENT_SIZE));
            return false;
        }
        if ($.inArray(type, ALLOWED_TYPES) == -1) {
            alert("Unsupported file type " + type);
            return false;
        }
        var listOfAlreadySelectedFilenames = $.grep($.map($("#documents_list input:file").not($elem), function(e){ return fileInputValueToName(e.value); }), function(e) { return e; });
        if ($.inArray(name, listOfAlreadySelectedFilenames) >= 0) {
            alert("File with that name is already selected for upload");
            return false;
        }
        return true;
    }
    function insertAnotherUploader() {
        if (document_counter.get() < MAX_DOCUMENT_COUNTER) {
            var instantiated_template = $(UPLOADER_TPL);
            var allowedTypesList = $('.allowed_filetypes', instantiated_template);
            $.each(ALLOWED_TYPES, function(_, type) {
                allowedTypesList.append($("<li>").text(type));
            });
            $('.allowed_filesize', instantiated_template).text(humanize_size(MAX_DOCUMENT_SIZE));
            $(":file", instantiated_template).bind("change", function(event) {
                var size = -1, type = "???", name = "";

                if (this.value == '') return;

                if (window.FileReader && this.files && this.files[0]) {
                    size = this.files[0].size;
                }
                name = fileInputValueToName(this.value);
                type = type_from_extension(name.split('.').pop());
                if (isValid($(this), name, size, type)) {
                    notifyUploadDataChange(instantiated_template, name, size, type);
                    var details = $(".file_assign_details", instantiated_template);
                    $.each(DOMAINS, function(_, domain) {
                        var inputName = "documentsDomains[" + name + "][" + domain + "]";
                        var e = $('<label><input type="checkbox" checked="" name="' + inputName + '"> ' + domain + '</label>');
                        details.append(e);
                    });
                    $('.upload_next', instantiated_template).hide();
                    $('.uploaded_file_details', instantiated_template).show();
                    document_counter.inc();
                    insertAnotherUploader();
                    $(document).trigger("uploaderFileCountChange", document_counter.get());
                } else {
                    $(this).wrap("<form>").closest("form").get(0).reset();
                    $(this).unwrap();
                }
            });
            $(".remove_upload", instantiated_template).bind("click", function(event) {
                instantiated_template.remove();
                document_counter.dec();
                if (document_counter.get() == MAX_DOCUMENT_COUNTER - 1) {
                    insertAnotherUploader();
                }
                event.preventDefault();
                $(document).trigger("uploaderFileCountChange", document_counter.get());
            });
            var editing_domains = false;
            $(".uploaded_file_details", instantiated_template).hide();
            $("[databind=domain_count_description]", instantiated_template).text("all domains");
            $(".assign_edit", instantiated_template).bind("click", function(event) {
                if (editing_domains) {
                    $(this).text("Edit");
                    var all_domains = DOMAINS.length,
                        selected_domains = $(":checkbox:checked", instantiated_template).size();
                    var domain_count_description = all_domains == selected_domains ? "all domains" : selected_domains + " out of " + all_domains + " domains";
                    $("[databind=domain_count_description]", instantiated_template).text(domain_count_description);
                } else {
                    $(this).text("Done editing");
                }
                $(".file_assign_summary", instantiated_template).toggle();
                $(".file_assign_details", instantiated_template).toggle();
                editing_domains = !editing_domains;
                event.preventDefault();
            });
            $(".upload_next", instantiated_template).show();
            $(".file_assign_details", instantiated_template).hide();
            $("#documents_list").append(instantiated_template);
            document_counter.sync();
        }
    }
    insertAnotherUploader();
});
</script>

<div>
    <h3 style="font-size: 1em; font-weight: bold;">Documents</h3>
</div>
<div>
    Selected <span databind="documents_counter">0</span> of <span databind="documents_count_limit"></span> allowed documents.
    <ol id="documents_list"></ol>
</div>
