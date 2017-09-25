'use strict';

const five = require('johnny-five');
const express = require('express');
const app = express();
const server = require('http').createServer(app);
const io = require('socket.io')(server);

let led = null;
var client_io;

app.use(express.static(__dirname + '/public'));
app.get('/', function(req, res, next) {
  res.sendFile(__dirname + '/index.html')
});
five.Board().on('ready', function() {
  console.log('Arduino is ready.');
 	var sensor = new five.Sensor({
	pin: "A2", 
	freq: 250, 
	threshold: 5
	});
  sensor.on("data", function() {
		if (typeof io != 'undefined'){
			if (typeof io.broadcast != 'undefined'){
				io.emit('gsr', this.value);
			}
		}
		if (typeof client_io != 'undefined'){
			client_io.emit('gsr', this.value);
		}
		console.log(this.value);
  });
  io.on('connection', function(client) {
    client_io = client;
	client.on('join', function(handshake) {
      console.log(handshake);
    });
	client.on('gsr', function(data){
		client.emit('gsr', data);
		}
	);
  });
});

const port = process.env.PORT || 3000;
server.listen(port);
console.log(`Server listening on http://localhost:${port}`);