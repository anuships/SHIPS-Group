var canvas = document.getElementById('canvas');
var time = 0;
//var start_button = document.createElement("button");
//start_button.innerHTML = "start EMDR";

function init() {
   window.requestAnimationFrame(draw);
}

function draw() {
   var ctx = canvas.getContext('2d');
   ctx.clearRect(0, 0, canvas.width, canvas.height); //clear canvas
   ctx.fillStyle = "white";
   ctx.fillRect(0, 0, canvas.width, canvas.height);
   if (time % 80 < 40) {
      time++;
      ctx.translate(10,0);
      ctx.beginPath();
      ctx.arc(0, 150, 50, 0, 2*Math.PI);
      ctx.fillStyle = "blue";
      ctx.fill();
      ctx.stroke();
   } else {
      time++;
      ctx.translate(-10,0);
      ctx.beginPath();
      ctx.arc(0, 150, 50, 0, 2*Math.PI);
      ctx.fillStyle = "blue";
      ctx.fill();
      ctx.stroke();
   }
   
   if (time < (15 * 80) - 20) {
      window.requestAnimationFrame(draw);
   }
}

//init();


