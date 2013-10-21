package drm.taskworker.workers.geo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BoundedInputStream;

public class SerialisableImageContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 359085421628984358L;

	private BufferedImage myimage;

	public SerialisableImageContainer() {
	}

	public SerialisableImageContainer(BufferedImage myimage) {
		super();
		this.myimage = myimage;
	}

	public BufferedImage getImage() {
		return myimage;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		if (myimage == null) {
			out.writeInt(0);
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(myimage, "png", baos);
			out.writeInt(baos.size());
			IOUtils.write(baos.toByteArray(), out);
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		int size = in.readInt();
		if(size!=0)
			myimage = ImageIO.read(new BoundedInputStream(in, size));
	}

	private void readObjectNoData() throws ObjectStreamException {

	}

}
