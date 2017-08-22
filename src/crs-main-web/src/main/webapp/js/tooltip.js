$(document).ready(function() {
    $('.tooltipped').hover(
            function () {
                var selfid = $(this).attr('id');
                var tooltipid = '#tooltip-for-'+selfid;
                $(tooltipid).css({'display': 'block',
                        'position': 'absolute',
                        'left': $(this).offset().left+$(this).width(),
                        'top': $(this).offset().top+$(this).height(),
                        'z-index': '999'
                });
            },
            function () {
                var selfid = $(this).attr('id');
                var tooltipid = '#tooltip-for-'+selfid;
                $(tooltipid).css('display', 'none');
            }
    );
})
