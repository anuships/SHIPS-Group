var canvas = document.getElementById('canvas');
alert('sees changes');

function init() {
   alert('reached init');
   window.requestAnimationFrame(draw);
}

function draw() {
   var ctx = canvas.getContext('2d');
   ctx.clearRect(0, 0, canvas.width, canvas.height); //clear canvas
   ctx.fillStyle = "white";
   ctx.fillRect(0, 0, canvas.width, canvas.height);
   ctx.translate(70,0);
   ctx.beginPath();
   ctx.arc(100, 400, 50, 0, 2*Math.PI);
   ctx.fillStyle = "blue";
   ctx.fill();
   ctx.stroke();
   
   window.requestAnimationFrame(draw);
}

init();


