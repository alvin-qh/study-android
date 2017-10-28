import net from "net";
import config from "../config";

function createServer() {
	return net.createServer(socket => {
		console.log(`Connect: ${socket.remoteAddress}:${socket.remotePort}`);

		socket.setEncoding('binary');

		socket.setTimeout(config.timeout, () => {
			console.log('Client connection timeout');
			socket.end();
		});

		socket.on('data', data => {
			console.log(`Receive: ${data}`);
		});

		socket.on('error', exception => {
			console.log(`Socket error: ${exception}`);
			socket.end();
		});

		socket.on('close', data => {
			console.log(`Close: ${socket.remoteAddress} ${socket.remotePort}`);
		});
	});
}

const server = createServer().listen(config.port, "0.0.0.0");

server.on('listening', () => {
	console.log(`Server listening: ${server.address().port}`);
});

server.on("error", exception => {
	console.log(`Server error: ${exception}`);
});


