$(document).ready(function() {
  var slideIndex = 1;
  showSlide(slideIndex);

  $(".prev-btn").click(function() {
    slideIndex--;
    if (slideIndex < 1) {
      slideIndex = $(".product.slide").length;
    }
    showSlide(slideIndex);
  });

  $(".next-btn").click(function() {
    slideIndex++;
    if (slideIndex > $(".product.slide").length) {
      slideIndex = 1;
    }
    showSlide(slideIndex);
  });

  function showSlide(n) {
    $(".product.slide").css("display", "none");
    $(".product.slide:nth-child(" + n + ")").css("display", "block");
  }
});
