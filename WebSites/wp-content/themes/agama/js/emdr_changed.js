var canvas = document.getElementById('canvas');
var time = 0;
var ctx = canvas.getContext('2d');
//var start_button = document.createElement("button");
//start_button.innerHTML = "start EMDR";
//var body = document.getElementsByTagName("body")[0];
//body.appendChild(start_button);
//button.addEventListener ("click", init());
//var start_button = document.getElementById('start_button');

//from user patriques on stack overflow
function getCursorPosition(canvas, event) {
   var rect = canvas.getBoundingClientRect();
   var x = event.clientX - rect.left;
   var y = event.clientY - rect.top;
   select_colour(x, y);
}

var inColourSelectionCanvas = true;

var SQUARE_WIDTH = 100;
var SQUARE_SEPARATION = 30;
var LEFT_BUFFER = 20;
var TOP_BUFFER = 60;

var emdr_colour = "blue";

function start_button_click() {
   var start_button = document.getElementById('start_button');
   start_button.style.visibility="hidden";
   //draw_selection();
   init();
}

function init() {
   window.requestAnimationFrame(draw);
}

function select_colour(x_coord, y_coord) {
   if (inColourSelectionCanvas) {
      if ((x_coord >= LEFT_BUFFER && x_coord <= LEFT_BUFFER + SQUARE_WIDTH) && (y_coord >=TOP_BUFFER && y_coord <= TOP_BUFFER + SQUARE_WIDTH)) {
         emdr_colour = "blue";
         draw_selection();
      } else if ((x_coord >= LEFT_BUFFER + SQUARE_WIDTH + SQUARE_SEPARATION && x_coord <= LEFT_BUFFER + 2*SQUARE_WIDTH + SQUARE_SEPARATION) && (y_coord >=TOP_BUFFER && y_coord <= TOP_BUFFER + SQUARE_WIDTH)) {
         emdr_colour = "green";
         draw_selection();
      } else if ((x_coord >= LEFT_BUFFER + 2*SQUARE_WIDTH + 2*SQUARE_SEPARATION && x_coord <= LEFT_BUFFER + 3*SQUARE_WIDTH + 2*SQUARE_SEPARATION) && (y_coord >=TOP_BUFFER && y_coord <= TOP_BUFFER + SQUARE_WIDTH)) {
         emdr_colour = "pink";
         draw_selection();
      } else if ((x_coord >= LEFT_BUFFER + 3*SQUARE_WIDTH + 3*SQUARE_SEPARATION && x_coord <= LEFT_BUFFER + 4*SQUARE_WIDTH + 3*SQUARE_SEPARATION) && (y_coord >=TOP_BUFFER && y_coord <= TOP_BUFFER + SQUARE_WIDTH)) {
         emdr_colour = "violet";
         draw_selection();
      } else if ((x_coord >= LEFT_BUFFER + 4*SQUARE_WIDTH + 4*SQUARE_SEPARATION && x_coord <= LEFT_BUFFER + 5*SQUARE_WIDTH + 4*SQUARE_SEPARATION) && (y_coord >=TOP_BUFFER && y_coord <= TOP_BUFFER + SQUARE_WIDTH)) {
         emdr_colour = "gray";
         draw_selection();
      }
   }
}

function draw_selection() {
   ctx.clearRect(0, 0, canvas.width, canvas.height); //clear canvas
   ctx.fillStyle = "white";
   ctx.fillRect(0, 0, canvas.width, canvas.height);
  
   ctx.font = "24px Arial";
   ctx.fillStyle = "red";
   ctx.textAlign = "center";
   ctx.fillText("Select a colour", canvas.width/2, 20);
   
   ctx.beginPath();
   if (emdr_colour == "blue") {
      ctx.lineWidth="6.0";
      ctx.strokeStyle="black";
   } else {
      ctx.lineWidth="1.0";
   }
   ctx.rect(LEFT_BUFFER, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "blue";
   ctx.fill();
   ctx.stroke();
   ctx.lineWidth="1.0";
   
   ctx.beginPath();
   if (emdr_colour == "green") {
      ctx.lineWidth="6.0";
      ctx.strokeStyle="black";
   } else {
      ctx.lineWidth="1.0";
   }
   ctx.rect(LEFT_BUFFER + SQUARE_WIDTH + SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "green";
   ctx.fill();
   ctx.stroke();
   ctx.lineWidth="1.0";
   
   ctx.beginPath();
   if (emdr_colour == "pink") {
      ctx.lineWidth="6.0";
      ctx.strokeStyle="black";
   } else {
      ctx.lineWidth="1.0";
   }
   ctx.rect(LEFT_BUFFER + 2*SQUARE_WIDTH + 2*SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "pink";
   ctx.fill();
   ctx.stroke();
   ctx.lineWidth="1.0";
   
   ctx.beginPath();
   if (emdr_colour == "violet") {
      ctx.lineWidth="6.0";
      ctx.strokeStyle="black";
   } else {
      ctx.lineWidth="1.0";
   }
   ctx.rect(LEFT_BUFFER + 3*SQUARE_WIDTH + 3*SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "violet";
   ctx.fill();
   ctx.stroke();
   ctx.lineWidth="1.0";
   
   ctx.beginPath();
   if (emdr_colour == "gray") {
      ctx.lineWidth="6.0";
      ctx.strokeStyle="black";
   } else {
      ctx.lineWidth="1.0";
   }
   ctx.rect(LEFT_BUFFER + 4*SQUARE_WIDTH + 4*SQUARE_SEPARATION, TOP_BUFFER, SQUARE_WIDTH, SQUARE_WIDTH);
   ctx.fillStyle = "gray";
   ctx.fill();
   ctx.stroke();
   ctx.lineWidth="1.0";
   
}

function draw() {
   inColourSelectionCanvas = false;
   ctx.clearRect(0, 0, canvas.width, canvas.height); //clear canvas
   ctx.fillStyle = "white";
   ctx.fillRect(0, 0, canvas.width, canvas.height);
   if (time % 80 < 40) {
      time++;
      ctx.translate(10,0);
      ctx.beginPath();
      ctx.arc(100, 200, 50, 0, 2*Math.PI);
      ctx.fillStyle = emdr_colour;
      ctx.fill();
      ctx.stroke();
   } else {
      time++;
      ctx.translate(-10,0);
      ctx.beginPath();
      ctx.arc(100, 200, 50, 0, 2*Math.PI);
      ctx.fillStyle = emdr_colour;
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


