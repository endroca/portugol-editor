/*
 * Semestralni prace KIV/OS - Spravce virtualnich stroju
 * Natalia Rubinova, A080082P
 * Martin Sloup, A08N0111P
 * Jiri Kucera, A08N0092P
 *
 */
package br.com.console;

import java.util.concurrent.Semaphore;

/**
 * The thread safe byte buffer queue<br />
 * Based on Spencer Rupport code from <a href="http://www.siafoo.net/snippet/216">http://www.siafoo.net/snippet/216</a>
 * <br />
 * <br />
 * * Added unlimited buffer size<br />
 * * Added javadoc comments
 */
public class ByteBufferQueue {

	private byte[] buff;
	private volatile int head,  tail,  count,  size;
	private Semaphore appendsem,  readsem,  countsem;

	/**
	 * Default constructor for {@link ByteBufferQueue}. The inside buffer is initialized for 256 items.
	 */
	public ByteBufferQueue() {
		this(256);
	}

	/**
	 * Constructor for defining the initial size of inside buffer
	 * @param size		initial size of the buffer
	 */
	public ByteBufferQueue(int size) {
		int realSize = 1;

		//use the power of two
		for (; realSize < size; realSize *= 2) {
		}

		//initialize the semaphores
		appendsem = new Semaphore(1, true);
		readsem = new Semaphore(1, true);
		countsem = new Semaphore(1, true);

		this.size = realSize;
		buff = new byte[realSize];

		head = 0;
		tail = 0;
		count = 0;
	}

	/**
	 * Reallocate inside buffer for minimal capacity specified by the parameter
	 * @param minimumCapacity		the new minimal capacity of inside buffer
	 */
	private void realocate(int minimumCapacity) {
		try {
			appendsem.acquire();
			readsem.acquire();
		} catch (InterruptedException e) {
			return;
		}

		int newSize = size;
		for (; newSize < minimumCapacity; newSize *= 2) {
		}

		byte newBuff[];
		int n1, n2;

		if (head <= tail) {
			// Create larger array and copy
			// data[front]...data[rear] into it.

			newBuff = new byte[newSize];
			System.arraycopy(buff, head, newBuff, head, count);
		} else {
			// Create a bigger array, but be careful about copying items
			// into it. The queue items
			// occur in two segments. The first segment goes from data[front] to
			// the end of the
			// array, and the second segment goes from data[0] to data[rear].
			// The variables n1
			// and n2 will be set to the number of items in these two segments.
			// We will copy
			// these segments to biggerArray[0...manyItems-1].

			newBuff = new byte[newSize];
			n1 = size - head;
			n2 = tail + 1;

			System.arraycopy(buff, head, newBuff, 0, n1);
			System.arraycopy(buff, 0, newBuff, n1, n2);

			head = 0;
			tail = count - 1;
		}

		buff = newBuff;

		try {
			countsem.acquire();
		} catch (InterruptedException e) {
			return;
		}
		size = newSize;


		countsem.release();
		appendsem.release();
		readsem.release();
	}

	/**
	 * Get the number of bytes in the buffer
	 * @return		number of the bytes in buffer
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Append byte to the end of buffer
	 * @param data		byte to be appended
	 */
	public void append(byte data) {
		append(new byte[]{data});
	}

	/**
	 * Append bytes to the end of buffer
	 * @param data		bytes to be appended
	 */
	public void append(byte[] data) {
		if (data != null) {
			append(data, 0, data.length);
		}
	}

	/**
	 * Append bytes to the end of buffer
	 * @param data		bytes to be appended
	 * @param offset	offset position in data from the bytes be appended
	 * @param length	count of appended bytes
	 */
	public void append(byte[] data, int offset, int length) {
		if (data == null) {
			return;
		}
		if (data.length < offset + length) {
			throw new RuntimeException(
					"array index out of bounds. offset + length extends beyond the length of the array.");
		}

		// Test if is needed to increase buffer size
		if (buff.length < count + length) {
			realocate(count + length);
		}

		try {
			appendsem.acquire();
		} catch (InterruptedException e) {
			return;
		}
		// We need to acquire the semaphore so that this.tail doesn't change.
		for (int i = 0; i < length; i++) {
			buff[(i + this.tail) % this.size] = data[i + offset];
		}
		this.tail = (length + this.tail) % this.size;
		try {
			countsem.acquire();
		} catch (InterruptedException e) {
			return;
		}
		// We need to acquire the semaphore so that this.count doesn't change.
		this.count = this.count + length;
		countsem.release();
		appendsem.release();
	}

	/**
	 * Reads some number of bytes from the input stream and stores them into
	 * the array <code>data</code>. The number of bytes actually read is
	 * returned as an integer.
	 *
	 * </p><p> The <code>read(data)</code> method for class {@link ByteBufferQueue}
	 * has the same effect as: </p>
	 * <pre><code>read(data, 0, data.length)</code></pre>
	 * @param data		byte array, where will be read bytes stored
	 * @return			count of read bytes
	 */
	public int read(byte[] data) {
		if (data != null) {
			return read(data, 0, data.length);
		} else {
			return 0;
		}
	}

	/**
	 * Reads up to <code>length</code> bytes of data from the buffer into an array of
	 * bytes. An attempt is made to read as many as <code>length</code> bytes, but a
	 * smaller number may be read, possibly zero. The number of bytes actually read is
	 * returned as an integer.
	 *
	 * @param data		byte array, where will be read bytes stored
	 * @param offset	the start offset in array <code>data</code> at which the data is written.
	 * @param length 	the maximum number of bytes to read
	 * @return			the total number of bytes read into the buffer
	 */
	public int read(byte[] data, int offset, int length) {
		if (data == null) {
			return 0;
		}
		if (data.length < offset + length) {
			throw new RuntimeException("array index out of bounds. offset + length extends beyond the length of the array.");
		}

		int readlength = 0;

		try {
			readsem.acquire();
		} catch (InterruptedException e) {
			return 0;
		}

		// We need to acquire the semaphore so that this.head doesn't change.
		for (int i = 0; i < length; i++) {
			if (i == count) {
				break;
			}
			data[i + offset] = buff[(i + head) % this.size];
			readlength++;
		}

		this.head = (readlength + this.head) % this.size;

		try {
			countsem.acquire();
		} catch (InterruptedException e) {
			readsem.release();
			return 0;
		}

		// We need to acquire the semaphore so that this.count doesn't change.
		this.count = this.count - readlength;

		countsem.release();
		readsem.release();

		return readlength;
	}

	/**
	 * Reads some number of bytes from the input stream and stores them into
	 * the array <code>data</code>. After the reading, read bytes remain in the
	 * buffer. The number of bytes actually read is returned as an integer.
	 *
	 * </p><p> The <code>peak(data)</code> method for class {@link ByteBufferQueue}
	 * has the same effect as: </p>
	 * <pre><code>peak(data, 0, data.length)</code></pre>
	 * @param data		byte array, where will be read bytes stored
	 * @return			count of read bytes
	 */
	public int peek(byte[] data) {
		if (data != null) {
			return peek(data, 0, data.length);
		} else {
			return 0;
		}
	}

	/**
	 * Reads up to <code>length</code> bytes of data from the buffer into an array of
	 * bytes. After the reading, read bytes remain in the buffer. An attempt is made to
	 * read as many as <code>length</code> bytes, but a smaller number may be read,
	 * possibly zero. The number of bytes actually read is returned as an integer.
	 *
	 * @param data		byte array, where will be read bytes stored
	 * @param offset	the start offset in array <code>data</code> at which the data is written.
	 * @param length 	the maximum number of bytes to read
	 * @return			the total number of bytes read into the buffer
	 */
	public int peek(byte[] data, int offset, int length) {
		if (data == null) {
			return 0;
		}

		if (data.length < offset + length) {
			throw new RuntimeException("array index out of bounds. offset + length extends beyond the length of the array.");
		}

		int readlength = 0;

		try {
			readsem.acquire();
		} catch (InterruptedException e) {
			return 0;
		}

		// We need to acquire the semaphore so that this.head doesn't change.
		for (int i = 0; i < length; i++) {
			if (i == count) {
				break;
			}
			data[i + offset] = buff[(i + head) % this.size];
			readlength++;
		}

		readsem.release();

		return readlength;
	}

	/**
	 * Read the whole buffer a return it as byte array.
	 * @return		array of bytes
	 */
	public byte[] readBytes() {

		byte[] data = new byte[count];
		try {
			read(data);
		} catch (Exception ex) {
		}
		return data;
	}
}