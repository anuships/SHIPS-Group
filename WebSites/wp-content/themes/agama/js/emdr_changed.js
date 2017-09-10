var canvas = document.getElementById('canvas');
var time = 0;
var start_button = document.getElementById('start_button');
var ctx = canvas.getContext('2d');

function start_button_click() {
   start_button.style.visibility="hidden";
   init();
}

function init() {
   window.requestAnimationFrame(draw);
}

function draw() {
   ctx.clearRect(0, 0, canvas.width, canvas.height); //clear canvas
   ctx.fillStyle = "white";
   ctx.fillRect(0, 0, canvas.width, canvas.height);
   if (time % 80 < 40) {
      time++;
      ctx.translate(10,0);
      ctx.beginPath();
      ctx.arc(100, 200, 50, 0, 2*Math.PI);
      ctx.fillStyle = "blue";
      ctx.fill();
      ctx.stroke();
   } else {
      time++;
      ctx.translate(-10,0);
      ctx.beginPath();
      ctx.arc(100, 200, 50, 0, 2*Math.PI);
      ctx.fillStyle = "blue";
      ctx.fill();
      ctx.stroke();
   }
   
   if (time < (15 * 80) - 20) {
      window.requestAnimationFrame(draw);
   } else {
      reset()
   }
   
}

function reset() {
   time = 0;
   ctx.translate(-200, 0);
   start_button.style.visibility="visible";
}


//init();


