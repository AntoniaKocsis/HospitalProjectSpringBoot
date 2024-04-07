window.addEventListener('scroll', function () {
    var cards = document.querySelectorAll('.w3-card-4');
    cards.forEach(function (card) {
        var position = card.getBoundingClientRect();

        // If the top of the card is in the viewport and it's not visible yet, reveal it with a smooth transition
        if (position.top <= window.innerHeight && position.bottom >= 0 && getComputedStyle(card).opacity === "0") {
            card.style.opacity = '1';
        } else if (position.bottom < 0 || position.top > window.innerHeight) {
            // If the card is out of the viewport, reset its opacity to hide it
            card.style.opacity = '0';
        }
    });
});