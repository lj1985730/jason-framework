!function ($) {

    'use strict';

    $(function() {

        $('.sort_list').on('click', function () {
            var id = $(this).attr('id');
            window.location.href = protocol + '//' + host + "/addrlist/site/addrlist/contactDetatil?id=" + id;
        });

        $('#button_back').on('click', function () {
            window.location.href = protocol + '//' + host + "/addrlist/site/addrlist/contactList";
        });
    });


}(window.jQuery);
