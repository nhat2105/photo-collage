import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;


public class PhotoCollage{
    public static void main(String[] args) {

        //Read users input for rows, columns, photos
        Scanner scanner = new Scanner(System.in);  //Create a scanner object
        System.out.println("Enter number of rows: ");

        int rows = Integer.parseInt(scanner.nextLine());  //Read rows input
        System.out.println("Enter number of columns: ");
        int columns = Integer.parseInt(scanner.nextLine()); //Read columns input
        
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        int counter = 0;
        while (counter < rows * columns){
            System.out.println("Enter the file name, or `quit` to stop: ");
            String img = scanner.nextLine();
            if (img.equals("quit"))break;
            else {
                try {
                    images.add(ImageIO.read(new File(img)));
                    counter++;
                } catch (IOException e) {
                    System.out.println("Guess what, there is an error");
                }
            }
        }
        scanner.close();

        //System.out.println("Number of rows: " + rows);
        //System.out.println("Number of columns: "+ columns);
        //System.out.println("Images array: " + images);

        //Merge images based on columns and rows
        int WIDTH = 2048;
        int HEIGHT = 1860 ; //Numbers works for 4 rows, 2 cols
        int widthPerImg = WIDTH/columns;
        int heightPerImg = HEIGHT/rows;

        //Pre-process - resizing images
        for (int i = 0; i < images.size(); i++){
            BufferedImage img = images.get(i);
            BufferedImage resizedImage = new BufferedImage(widthPerImg, heightPerImg, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(img.getScaledInstance(widthPerImg, heightPerImg, Image.SCALE_SMOOTH), 0, 0, widthPerImg, heightPerImg, null);
            graphics2D.dispose();

            images.set(i, resizedImage);
        }        

        //Create blank canvas
        BufferedImage collage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = collage.createGraphics();

        //Draw images onto collage based on rows and height
        int temp = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                BufferedImage img = images.get(temp);
                g.drawImage(img, j * widthPerImg, i * heightPerImg, null);
                temp++;
            }
        }
 
        //Saving the collage
        try {
            ImageIO.write(collage, "PNG", new File("collage.png"));
        } catch (IOException e) {
            System.out.println("Error saving collage");
        }
        
    }
}


