import crypto from "crypto";
import {Buffer} from "buffer";

function verify(data) {
	const hash = crypto.createHash('md5').update(data.message).digest('hex');
	return data.checksum.toString('hex') === hash;
}

class Protocol {

	constructor() {
		this.buffer = Buffer.alloc(0);
		this.reset();
	}

	reset() {
		this.length = null;
		this.checksum = null;
		this.message = null;
	}

	unpack(data, callback) {
		this.buffer = Buffer.concat([this.buffer, Buffer.from(data)]);

		if (this.length === null && this.buffer.length >= 4) {
			this.length = this.buffer.readInt32BE(0);
			this.buffer = this.buffer.slice(4);
		}

		if (this.checksum === null && this.buffer.length >= 16) {
			this.checksum = this.buffer.slice(0, 16);
			this.buffer = this.buffer.slice(16);
		}

		if (this.message === null && this.buffer.length >= this.length) {
			this.message = this.buffer.slice(0, this.length);
			this.buffer = this.buffer.slice(this.length);

			const result = {
				length: this.length,
				checksum: this.checksum,
				message: this.message
			};
			this.reset();

			if (!verify(result)) {
				callback(null, new Error("Invalid checksum"));
				return;
			}

			let message;
			try {
				message = JSON.parse(result.message.toString('utf8'));
				if (!message) {
					callback(null, new Error("Invalid message"));
					return;
				}
			} catch (e) {
				callback(null, new Error("Invalid message"));
				return;
			}
			callback(message);
		}
	}

	static package(json) {
		const message = Buffer.from(JSON.stringify(json), 'utf8');

		const length = Buffer.alloc(4);
		length.writeInt32BE(message.length, 0);

		const checksum = crypto.createHash('md5').update(message).digest();

		return Buffer.concat([length, checksum, message]);
	}
}

export default Protocol;
