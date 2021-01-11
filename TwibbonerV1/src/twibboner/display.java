/**
 *  Sesuai namanya, Class ini menjadi display (UI) gambar pada program ini
 *
 *  Author  : Thomas Dwi Awaka
 *  Version : 1
 */

package twibboner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class display extends JPanel implements ImageObserver {

    private Image gambar,twbn;
    private int batasLebar = 1020;
    private int batasTinggi = 660;
    static int lebarBaru,tinggiBaru;

    //----------------------------------------------------------//

    //constructor
    public display() {
        draw();
    }

    /**
     * membaca file gambar dan merubah ukurannya
     * @param file gambar yang akan dibaca
     */
    public void setGambar(String file) {
        try {
            this.gambar = ImageIO.read(new File(file));
            twibbon.gambar = this.gambar;

            //di-resize sesuai dengan batas lebar & tinggi
            Dimension resizedDimensions = twibbon.resize(gambar.getWidth(null),
                    gambar.getHeight(null), batasLebar, batasTinggi);
            lebarBaru = resizedDimensions.width;
            tinggiBaru = resizedDimensions.height;

        } catch (IOException e) {}
    }

    /**
     * membaca file twibbon dan merubah ukurannya
     * @param file twibbon
     */
    public void setTwbn(String file) {
        try {
            this.twbn = ImageIO.read(new File(file));
            twibbon.twbn = this.twbn;
        } catch (IOException e) {}
    }

    /**
     * membuat layout dari seluruh isi program ini
     */
    public void draw() {

        // menciptakan layout
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1080, 720));
        revalidate();
        repaint();
        this.setVisible(true);

        this.add(new drawG2D());

        // untuk gambar yang akan muncul di display
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        paintComponent(g);
    }

    /**
     * Proses Rendering gambar di layout
     */
    private class drawG2D extends JComponent {

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D graphics2D = (Graphics2D) g;
            // mengontrol kualitas & waktu rendering gambar dan text
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

            //memb-draw background preview
            graphics2D.setColor(Color.PINK);
            graphics2D.fillRect(0, 30, batasLebar, batasTinggi);

            //mem-draw gambar yang telah di-resize
            graphics2D.drawImage(twibbon.getGambar(), (batasLebar - lebarBaru) / 2,
                    (batasTinggi - tinggiBaru) / 2 + 30, lebarBaru, tinggiBaru, null);

            // untuk tulisan di atas kotak preview
            graphics2D.setColor(Color.BLACK);
            Font font = new Font("Montserrat", Font.BOLD, 20);
            FontMetrics metrics = g.getFontMetrics(font);

            //untuk membuat posisi teks selalu ditengah
            int x = (batasLebar - metrics.stringWidth(detailPanel.pesanSistem))/2;
            graphics2D.setFont(font);
            graphics2D.drawString(detailPanel.pesanSistem,x, 20);
        }
    }
}
