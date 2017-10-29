import {expect} from "chai";

import net from "net";

import Protocol from "../app/protocol";
import {close} from "../app/socket-server";
import config from "../config";

describe('Test socket server', () => {

	after(() => {
		close();
	});

	it('test connect', cb => {
		const socket = net.connect(config.port, 'localhost', () => {
			socket.destroy();
			cb();
		});
	});

	it('test send data', cb => {
		const socket = net.connect(config.port, 'localhost', () => {
			socket.write(Protocol.package({cmd: 'time'}));
		});

		const proto = new Protocol();

		socket.on('data', data => {
			proto.unpack(data, (msg, err) => {
				if (err) {
					console.log(err);
					expect.fail();
					cb();
				}

				switch (msg.cmd) {
				case 'time-ack':
					expect(msg.value).to.be.a('string');

					socket.write(Protocol.package({cmd: 'bye'}));
					break;
				case 'bye-ack':
					expect(msg.value).to.be.undefined;
					break;
				default:
					expect.fail();
				}
			});
		});

		socket.on('close', () => {
			cb();
		});
	});
});