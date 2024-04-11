window.addEventListener('scroll', function () {
    var element = document.getElementById('prescriptions-paragraph');
    var position = element.getBoundingClientRect();

    // If the top of the paragraph is in the viewport and it's not visible yet, reveal it with a smooth transition
    if (position.top <= window.innerHeight && position.bottom >= 0 && getComputedStyle(element).opacity === "0") {
        element.style.opacity = '1';
    } else if (position.bottom < 0 || position.top > window.innerHeight) {
        // If the paragraph is out of the viewport, reset its opacity to hide it
        element.style.opacity = '0';
    }
});