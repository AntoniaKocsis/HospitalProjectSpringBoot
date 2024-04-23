// Define a class for managing sticky navigation behavior
class StickyNavigation {
    constructor() {
        // Initialize properties
        this.currentId = null; // ID of the currently active section
        this.currentTab = null; // Currently active tab element
        this.tabContainerHeight = 70; // Height of the tab container

        // Bind event handlers to the class instance
        let self = this;
        $('.et-hero-tab').click(function (event) {
            event.preventDefault(); // Prevent the default link behavior
            let href = $(this).attr('href');
            if (href.startsWith('#')) {
                self.onTabClick(event, href); // Handle tab click event
            } else {
                window.location.href = href; // Navigate to the link's href
            }
        });

        // Listen for scroll and resize events on the window
        $(window).scroll(() => {
            this.onScroll(); // Handle scroll event
        });
        $(window).resize(() => {
            this.onResize(); // Handle resize event
        });
    }

    // Handle click event on tabs
    onTabClick(event, href) {
        let scrollTop = $(href).offset().top - this.tabContainerHeight + 1;
        // Smoothly scroll to the corresponding section
        $('html, body').animate({scrollTop: scrollTop}, 600);
    }

    // Handle scroll event
    onScroll() {
        this.checkTabContainerPosition(); // Check position of tab container
        this.findCurrentTabSelector(); // Find currently active tab
    }

    // Handle resize event
    onResize() {
        if (this.currentId) {
            this.setSliderCss(); // Adjust slider CSS if there's an active tab
        }
    }

    // Check position of tab container and apply sticky behavior if necessary
    checkTabContainerPosition() {
        let offset = $('.et-hero-tabs').offset().top + $('.et-hero-tabs').height() - this.tabContainerHeight;
        if ($(window).scrollTop() > offset) {
            $('.et-hero-tabs-container').addClass('et-hero-tabs-container--top');
        } else {
            $('.et-hero-tabs-container').removeClass('et-hero-tabs-container--top');
        }
    }

    // Find the currently active tab based on scroll position
    findCurrentTabSelector(element) {
        let newCurrentId;
        let newCurrentTab;
        let self = this;
        $('.et-hero-tab').each(function () {
            let id = $(this).attr('href');
            let offsetTop = $(id).offset().top - self.tabContainerHeight;
            let offsetBottom = $(id).offset().top + $(id).height() - self.tabContainerHeight;
            if ($(window).scrollTop() > offsetTop && $(window).scrollTop() < offsetBottom) {
                newCurrentId = id;
                newCurrentTab = $(this);
            }
        });
        // Update current tab if it has changed
        if (this.currentId !== newCurrentId || this.currentId === null) {
            this.currentId = newCurrentId;
            this.currentTab = newCurrentTab;
            this.setSliderCss(); // Adjust slider CSS for the new active tab
        }
    }

    // Set CSS properties for the tab slider
    setSliderCss() {
        let width = 0;
        let left = 0;
        if (this.currentTab) {
            width = this.currentTab.css('width');
            left = this.currentTab.offset().left;
        }
        $('.et-hero-tab-slider').css('width', width);
        $('.et-hero-tab-slider').css('left', left);
    }
}

// Instantiate the StickyNavigation class to initialize the sticky navigation behavior
new StickyNavigation();
