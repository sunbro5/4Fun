package com.company;

import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 * @author <a href="mailto:mares.jan@o2.cz">Jan Mares</a>, 6.3.2018
 */
public class Transparency {
    public static Image makeColorTransparent
            (Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ( ( rgb | 0xFF000000 ) == markerRGB ) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}
