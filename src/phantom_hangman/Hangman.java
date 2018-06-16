package phantom_hangman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *Displays a selected series of hangman image to the game board
 * @author user
 */
public class Hangman extends JLabel {
   /**
    * The preferred width of the images
    */ 
    private final int PREFERRED_WIDTH;
    
    /**
     * The preferred height of the images
     */
    private final int PREFERRED_HEIGHT;
    
    /**
     * The base (common) name of the series of images to use (e.g. "hangman"
     * "hangman_0.png, hangman_1.png, ...")
     */
    private final String IMAGE_BASE_NAME;
    
    /**
     * The directory containing the hangman images.
     */
    private final String IMAGE_DIRECTORY;
    
    /**
     * The type of image (.jpg, .png, etc to iclude the period)
     */
    private final String IMAGE_TYPE;
    
    /**
     * The current path of the current image
     */
    private String path;
    
    /**
     * The current image being displayed
     */
    private BufferedImage image;
    
    /**
     * The default constructor
     */
    public Hangman() {
        this("hangman", "images/", ".png");
    }
    
    /**
     * Creates a new Hangman given the image series base name, the directory
     * of the hangman images, and the type of image
     * @param imageBaseName the base name of the image series to be used
     * @param imageDirectory the directory containing the hangman images
     * @param imageType the type of the hangman images
     */
    public Hangman(String imageBaseName, String imageDirectory, String imageType) {
        PREFERRED_WIDTH = 440;
        PREFERRED_HEIGHT = 255;
        
        IMAGE_BASE_NAME = imageBaseName;
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;
        
        //you must suffix all images with _(image number) for this to work
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        path = IMAGE_DIRECTORY + IMAGE_BASE_NAME + "_0" + IMAGE_TYPE;
        image = loadImage(path);
    }
    
    /**
     * loads image from a file
     * @param imagePath the path to load an image from
     * @return  a BufferedImage object on success, exits on failure
     */
    private BufferedImage loadImage(String imagePath) {
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            System.err.println("loadImage(): Error: Image at " + imagePath + " could not be found");
            System.exit(1);
        }
        return img;
    }
    
    /**
     * load the next hangman image in the series
     * @param imageNumber the number of the image to load
     */
    public void nextImage (int imageNumber) {
        loadNewImage (String.valueOf(imageNumber));
    }
    
    /**
     * display the losing image
     */
    public void loseImage() {
        loadNewImage("lose");
    }
    
    /**
     * display the winning image
     */
    public void winImage() {
        loadNewImage("win");
    }

    /**
     * loads a new image in the hangman image series
     * @param suffix the suffix of the image name
     */
    private void loadNewImage(String suffix) {
        path = IMAGE_DIRECTORY + IMAGE_BASE_NAME + "_" + suffix + IMAGE_TYPE;
        image = loadImage(path);
        repaint();
    }
    
    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,
                0,
                0,
                PREFERRED_WIDTH,
                PREFERRED_HEIGHT,
                null);
    }
    
}
