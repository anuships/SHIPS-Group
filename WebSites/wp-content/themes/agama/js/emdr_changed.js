var canvas = document.getElementById('canvas');
var time = 0;
var ctx = canvas.getContext('2d');
//var start_button = document.createElement("button");
//start_button.innerHTML = "start EMDR";
//var body = document.getElementsByTagName("body")[0];
//body.appendChild(start_button);
//button.addEventListener ("click", init());
//var start_button = document.getElementById('start_button');


var SQUARE_WIDTH = 100;
var SQUARE_SEPARATION = 30;
var LEFT_BUFFER = 20;
var TOP_BUFFER = 20;

function start_button_click() {
   var start_button = document.getElementById('start_button');
   start_button.style.visibility="hidden";
   //draw_selection();
   init();
}

function init() {
   window.requestAnimationFrame(draw);
}

function draw_selection() {
   ctx.clearRect(0, 0, canvas.width, canvas.height); //clear canvas
   ctx.fillStyle = "white";
   ctx.fillRect(0, 0, canvas.width, canvas.height);
  
   
   ctx.beginPath();
   ctx.rect(LEFT_BUFFER, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "blue";
   ctx.fill();
   ctx.stroke();
   
   ctx.beginPath();
   ctx.rect(LEFT_BUFFER + SQUARE_WIDTH + SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "green";
   ctx.fill();
   ctx.stroke();
   
   ctx.beginPath();
   ctx.rect(LEFT_BUFFER + 2*SQUARE_WIDTH + 2*SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "pink";
   ctx.fill();
   ctx.stroke();
   
   ctx.beginPath();
   ctx.rect(LEFT_BUFFER + 3*SQUARE_WIDTH + 3*SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "violet";
   ctx.fill();
   ctx.stroke();
   
   ctx.beginPath();
   ctx.rect(LEFT_BUFFER + 4*SQUARE_WIDTH + 4*SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "gray";
   ctx.fill();
   ctx.stroke();
   
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
      time = 0;
      ctx.translate(-200, 0);
      reset_start_button();
   }
   
}
   
function reset_start_button() {
   start_button.style.visibility="visible";
}


draw_selection();


