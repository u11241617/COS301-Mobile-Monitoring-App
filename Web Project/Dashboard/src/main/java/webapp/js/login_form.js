$(window, document, undefined).ready(function() {

    $(document).on('blur','input', function() {

        var $this = $(this);

        if ($this.val())
            $this.addClass('used');
        else
            $this.removeClass('used');
    });


    var $ripples = $('.ripples');


    $(document).on('click.Ripples', '.ripples', function(e) {

        var $this = $(this);
        var $offset = $this.parent().offset();
        var $circle = $this.find('.ripplesCircle');

        var x = e.pageX - $offset.left;
        var y = e.pageY - $offset.top;

        $circle.css({
            top: y + 'px',
            left: x + 'px'
        });

        $this.addClass('is-active');

    });

    $(document).on('animationend webkitAnimationEnd mozAnimationEnd oanimationend MSAnimationEnd', '.ripples', function(e) {
        $(this).removeClass('is-active');
    });

});