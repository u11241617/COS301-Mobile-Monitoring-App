

$(document).ready(function() {

/*
    var tour = new Tour({
        storage: false,
        backdrop: true,
        steps: [
            {
                element: "#left-menu",
                placement: 'right',
                title: "Navigation",
                content: "This section allows you to navigate the site and view different content"
            },
            {
                element: "#device-list",
                placement: 'bottom',
                title: "Devices",
                content: "All the devices linked to this account"
            },
            {
                element: "#summary",
                placement: 'top',
                title: "Logs Summary",
                content: "A summary of some the the collected data on the current device"
            }
        ]});
        tour.init();
        tour.start();*/

    $(document).on('click', '.sidebar-menu>li', function(e) {

        var $this = $(this);
        $(".sidebar-menu>li.active").removeClass("active");
        $(this).addClass("active");
    });

    //$(".modal").modal('hide');

});
