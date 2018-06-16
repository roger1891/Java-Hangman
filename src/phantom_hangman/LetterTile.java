package phantom_hangman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *a tile representing a letter to be guessed during a game.
 * @author user
 */
public class LetterTile extends JLabel {
    /**
     * The letter to be displayed in the tile
     */
    private final char IMAGE_LETTER;
    
    /**
     * The directory containing the letter images
     */
    private final String IMAGE_DIRECTORY;
    
    /**
     * The type of image (.jpg, .png, etc to include the period)
     */
    private final String IMAGE_TYPE;
    
    /**
     * The preferred width of the tiles
     */
    private final int PREFERRED_WIDTH;
    
    /**
     * The preferred height of the tiles
     */
    private final int PREFERRED_HEIGHT;
    
    /**
     * The current path of the current image
     */
    private String path;
    
    /**
     * The current image being displayed
     */
    private BufferedImage image;
    
    /**
     * A custom MouseListener from the GameBoard class to change the tiles on a click
     */
    private MouseListener tileListener;
    
    /**
     * The default constructor
     */
    public LetterTile() {
        this('e', "images/", ".png");
    }
    
    /**
     * creates a new LetterTile given the letter to be displayed the directory
     * of the letter image series, and the image type
     * @param imageLetter the letter to be displayed on the tile
     * @param imageDirectory the directory holding the letter images
     * @param imageType the type of the letter images
     */
    public LetterTile (char imageLetter, String imageDirectory, String imageType) {
        IMAGE_LETTER = imageLetter;
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;
        
        PREFERRED_WIDTH = PREFERRED_HEIGHT = 50;
        
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        path = IMAGE_DIRECTORY + IMAGE_LETTER + IMAGE_TYPE;
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
     * changes the tile's appearance and removes the mouse listener to prevent
     * the tile from being clicked again
     * @return the tile's letter
     */
    public char guess() {
        loadNewImage("guessed");
        removeTileListener();
        return IMAGE_LETTER;
    }
    
    /**
     * loads a new image in the hangman image series
     * @param suffix the suffix of the image name
     */
    private void loadNewImage (String suffix) {
        path = IMAGE_DIRECTORY + IMAGE_LETTER + "_" + suffix + IMAGE_TYPE;
        image = loadImage(path);
        repaint();
    }
    
    /**
     * add a Tilelistener to the tile
     * @param l The MouseListener to attach as a TileListener
     */
    public void addTileListener(MouseListener l) {
        tileListener = l;
        addMouseListener(tileListener);
    }
    
    /**
     * remove the tile's TileListener
     */
    public void removeTileListener () {
        removeMouseListener(tileListener);
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
