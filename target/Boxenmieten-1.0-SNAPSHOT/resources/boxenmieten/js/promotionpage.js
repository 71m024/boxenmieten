$(document).ready(function() {
    var menuWrapper = $('#layout-menu-cover'),
    menuScrollContent = $('#layout-menu-cover-scroll-content'),
    menu = $('#layout-menu'),
    menuPopupButton = $('#mobile-menu-btn'),
    layoutMenuClick = false;
    
    menuWrapper.addClass('nano');
    menuScrollContent.addClass('nano-content');
    $(".nano").nanoScroller();
    setTimeout(function(){menuWrapper.addClass("Animated03");},500);
    
    // show and hide popup main menu when click a menu button
    menuPopupButton.off('click.popupMenubutton').on('click.popupMenubutton', function(e){
        layoutMenuClick = true;

        if(menuWrapper.hasClass('active')) {
            $(this).removeClass('active');
            menuWrapper.removeClass('active');
        }
        else {
            $(this).addClass('active');
            menuWrapper.addClass('active');
        }
        e.preventDefault();
    });
    
    menuWrapper.on('click', function() {
        layoutMenuClick = true;
    });
    
    this.clickNS = 'click.' + this.id;
    $(document.body).off(this.clickNS).on(this.clickNS, function (e) {
        if(!layoutMenuClick) {
            var activeMenuParent = menu.children('li.active-menu-parent');
            menuWrapper.removeClass('active');
            activeMenuParent.children('a').removeClass('active-menu');
            activeMenuParent.removeClass('active-menu-parent').children('ul').removeClass('active-menu');
            menuPopupButton.removeClass('active');
        }

        layoutMenuClick = false;
    });
});