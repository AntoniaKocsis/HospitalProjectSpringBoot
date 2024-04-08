window.addEventListener('scroll', function () {
    var element = document.getElementById('facilities');
    var position = element.getBoundingClientRect();

    // If the top of the Meet The Doctors section is in the viewport and it's not visible yet, reveal it with a smooth transition
    if (position.top <= window.innerHeight && position.bottom >= 0 && getComputedStyle(element).opacity === "0") {
        element.style.opacity = '1';
    } else if (position.bottom < 0 || position.top > window.innerHeight) {
        // If the Meet The Doctors section is out of the viewport, reset its opacity to hide it
        element.style.opacity = '0';
    }
});