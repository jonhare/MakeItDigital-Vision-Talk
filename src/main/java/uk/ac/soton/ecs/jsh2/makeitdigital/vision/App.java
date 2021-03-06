/**
 * Copyright (c) 2015, The University of Southampton.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * 	Redistributions of source code must retain the above copyright notice,
 * 	this list of conditions and the following disclaimer.
 *
 *   *	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 *
 *   *	Neither the name of the University of Southampton nor the names of its
 * 	contributors may be used to endorse or promote products derived from this
 * 	software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package uk.ac.soton.ecs.jsh2.makeitdigital.vision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openimaj.content.slideshow.PictureSlide;
import org.openimaj.content.slideshow.Slide;
import org.openimaj.content.slideshow.SlideshowApplication;
import org.openimaj.content.slideshow.VideoSlide;
import org.openimaj.video.VideoDisplay.EndAction;

/**
 * BBC Make It Digital: Computer Vision Talk
 */
public class App {
	private static BufferedImage background = null;
	static int SLIDE_WIDTH;
	static int SLIDE_HEIGHT;

	static {
		final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		final Dimension size = device.getDefaultConfiguration().getBounds().getSize();

		SLIDE_WIDTH = size.width;
		SLIDE_HEIGHT = SLIDE_WIDTH * 9 / 16;
	}

	/**
	 * Main method
	 *
	 * @param args
	 *            ignored
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final List<Slide> slides = new ArrayList<Slide>();

		slides.add(new PictureSlide(App.class.getResource("slides/slides.001.jpg")));
		slides.add(new PictureSlide(App.class.getResource("slides/slides.002.jpg")));
		slides.add(new PictureSlide(App.class.getResource("slides/slides.003.jpg")));
		slides.add(new BadTomatoDemo(App.class.getResource("slides/slides.004.jpg")));
		slides.add(new VideoSlide(App.class.getResource("tomato.mp4"), App.class.getResource("slides/slides.005.jpg"),
				EndAction.PAUSE_AT_END));
		slides.add(new PictureSlide(App.class.getResource("slides/slides.006.jpg")));
		slides.add(new VideoSlide(App.class.getResource("car.mp4"), App.class.getResource("slides/slides.007.jpg"),
				EndAction.PAUSE_AT_END));
		slides.add(new InmoovDemo(App.class.getResource("slides/slides.008.jpg")));

		slides.add(new PictureSlide(App.class.getResource("slides/slides.009.jpg")));
		slides.add(new ArtARDemo(App.class.getResource("slides/slides.010.jpg")));
		slides.add(new StickyFeaturesDemo(App.class.getResource("slides/slides.011.jpg")));
		slides.add(new PictureSlide(App.class.getResource("slides/slides.012.jpg")));
		slides.add(new PictureSlide(App.class.getResource("slides/slides.013.jpg")));
		slides.add(new CLMDemo(App.class.getResource("slides/slides.014.jpg")));
		slides.add(new PuppeteerDemo(App.class.getResource("slides/slides.015.jpg"))); // TODO
																						// someone
																						// other
																						// than
																						// Mark
		slides.add(new PictureSlide(App.class.getResource("slides/slides.016.jpg")));

		new SlideshowApplication(slides, SLIDE_WIDTH, SLIDE_HEIGHT, getBackground());
	}

	/**
	 * @return the generic slide background
	 */
	public synchronized static BufferedImage getBackground() {
		if (background == null) {
			background = new BufferedImage(SLIDE_WIDTH, SLIDE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
			final Graphics2D g = background.createGraphics();
			g.setColor(Color.WHITE);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.fillRect(0, 0, background.getWidth(), background.getHeight());

			final URL bgImageUrl = App.class.getResource("background.jpg");
			try {
				final BufferedImage b = ImageIO.read(bgImageUrl);
				g.drawImage(b, 0, 0, SLIDE_WIDTH, SLIDE_HEIGHT, null);
			} catch (final IOException e) {
			}
		}
		return background;
	}

	public static int getVideoWidth(int remainder) {
		final int avail = SLIDE_WIDTH - remainder;
		if (avail > 1280)
			return 1280;
		if (avail > 960)
			return 960;
		if (avail > 640)
			return 640;
		return 320;
	}

	public static int getVideoHeight(int remainder) {
		final int width = getVideoWidth(remainder);
		switch (width) {
		case 1280:
			return 720;
		case 960:
			return 540;
		case 640:
			return 480;
		}
		return 240;
	}
}
