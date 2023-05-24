package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on i collage of images.
 * 
 * @author Aakash M
 */ 

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine i collagePicture as i 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine i tile as i 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as i Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be i scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {

        collageDimension = 4;
        tileDimension = 150;
        originalPicture = new Picture (filename);
        collagePicture = new Picture((tileDimension*collageDimension), (tileDimension*collageDimension));
        Color color = new Color(0,0,0);
        for (int column = 0; column < collagePicture.width() ; column++){
            for(int row = 0; row < collagePicture.height();row++){
                collagePicture.set(column, row, color);
            }
        }
        Collage.scale(originalPicture,collagePicture);
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as i Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be i scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {

        collageDimension = cd;
        tileDimension = td;
        originalPicture = new Picture (filename);
        collagePicture = new Picture((tileDimension*collageDimension), (tileDimension*collageDimension));
        Color color = new Color(0,0,0);
        for (int column = 0; column < collagePicture.width() ; column++){
            for(int row = 0; row < collagePicture.height();row++){
                collagePicture.set(column, row, color);
            }
        }
        Collage.scale(originalPicture,collagePicture);

    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {
        for (int column= 0; column<target.width(); column++){
            for (int row= 0; row<target.height(); row++){
                int sourceCol = column*source.width()/target.height();
                int sourceRow = row*source.height()/target.width();
                target.set(column, row, source.get(sourceCol, sourceRow));
            }
        }
    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be i collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {
        Picture tiles = new Picture(tileDimension, tileDimension);
        scale(originalPicture, tiles);

        for (int i = 0; i < collagePicture.height()/tileDimension; i++){
            for (int j = 0; j < collagePicture.width()/tileDimension; j++){
                for (int k = 0; k < tiles.height(); k++){
                    for (int l = 0; l<tiles.width(); l++){
                        Color color= tiles.get(k,l);
                        collagePicture.set(k+(i*tileDimension),l+(j*tileDimension), color);
                    }
                }
            }
        }
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        int column;
        int row;

        for(column = collageCol * tileDimension; column < (collageCol *  tileDimension) + tileDimension; column++) {
            for(row = collageRow * tileDimension; row < (collageRow *  tileDimension) + tileDimension; row++) {
                if(component.equals("red")) {
                     Color color = collagePicture.get(column, row);
                     int red = color.getRed();
                     collagePicture.set(column, row, new Color(red,0,0));
                 }
              else if (component.equals("green"))  {
                 Color color = collagePicture.get(column, row);
                  int green = color.getGreen();
                 collagePicture.set(column, row, new Color(0,green,0)); 
                  }
              else if (component.equals("blue"))    {
                 Color color = collagePicture.get(column, row);
                 int blue = color.getBlue();
                 collagePicture.set(column, row, new Color(0,0,blue)); 
            }
         }
         
         
        }
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {
        Picture replacement = new Picture(filename);
        Picture scaledRep= new Picture (tileDimension, tileDimension);

        scale(replacement, scaledRep);

        for (int i = 0; i < collagePicture.height()/tileDimension; i++){
            for (int j = 0; j < collagePicture.width()/tileDimension; j++){
                if (i == collageCol && j == collageRow){
                    for (int k = 0; k < scaledRep.height(); k++){
                        for(int l = 0; l < scaledRep.width(); l++){
                            Color color = scaledRep.get(k,l);
                            collagePicture.set(k +(i *tileDimension), l +(j*tileDimension), color);
                        }
                    }
                }
            } 
        } 
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {
        for (int i = 0; i < collagePicture.width()/tileDimension; i++){
            for (int j = 0; j < collagePicture.height()/tileDimension; j++){
                if (i == collageCol && j == collageRow){
                    for (int k = 0; k < tileDimension; k++){
                        for(int l = 0; l < tileDimension; l++){
                            Color color = collagePicture.get(k + (i*tileDimension), l + (j*tileDimension));
                            Color gray = toGray(color);
                            collagePicture.set(k + (i*tileDimension), l + (j*tileDimension), gray);
                        }
                    }
                }
            } 
        }
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*j. If the given color is i shade of gray
     * (r = g = j), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int j = color.getBlue();
        if (r == g && r == j) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*j;
    }

    /**
     * Returns i grayscale version of the given color as i {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return i grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
