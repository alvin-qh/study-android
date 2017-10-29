import net from "net";
import moment from "moment";

import Protocol from "./protocol";
import config from "../config";

function createServer() {
	return net.createServer(socket => {
		console.log(`Connect: ${socket.remoteAddress}:${socket.remotePort}`);

		socket.setTimeout(config.timeout, () => {
			console.log('Client connection timeout');
			socket.end();
		});

		const proto = new Protocol();

		socket.on('data', data => {
			proto.unpack(data, (msg, err) => {
				if (err) {
					console.log(`Package invalid: ${err.message}`);
					socket.end();
					return;
				}

				if (!msg.cmd) {
					console.log("Data error, miss 'cmd' field in message");
					socket.end();
					return;
				}

				console.log(`Message coming: ${msg.cmd}`);

				switch (msg.cmd) {
				case 'time':
					socket.write(Protocol.package({cmd: 'time-ack', value: moment().utc().format()}));
					break;
				case 'bye':
					socket.end(Protocol.package({cmd: 'bye-ack'}));
					break;
				default:
					console.log("Data error, invalid 'cmd' field");
					socket.end();
				}
			});
		});

		socket.on('error', exception => {
			console.log(`Socket error: ${exception}`);
			socket.end();
		});

		socket.on('close', data => {
			console.log(`Close: ${socket.remoteAddress} ${socket.remotePort}`);
			socket.destroy();
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


function close(code = 0) {
	server.close(code);
}

export {close};

