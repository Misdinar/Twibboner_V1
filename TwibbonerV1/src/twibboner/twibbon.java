/**
 *  Class ini mengatur ukuran gambar yang masuk ke program.
 *  menyesusaikan ukuran dari gambar yang akan ditampilkan
 *  pada program
 *
 *  Author  : Thomas Dwi Awaka
 *  Version : 1
 */

package twibboner;

import java.awt.*;
import java.awt.image.BufferedImage;

public class twibbon {

    static Image gambar;
    static Image twbn;
    static Image gambarJadi;
    static int opacity;
    static int ukuran;
    static int posisi = 1;

    /**
     * mengambil gambar yang sudah masuk diprogram
     * @return hasil gambar
     */
    public static Image getGambar(){
        return gambarJadi;
    }

    /**
     * method ini meng-update tampilan pada program
     * setiap ada perubahan, seperti open/save foto
     */
    public static void update(){
        // jika gambar dan twibbon sudah dimasukkan di program
        if(gambar != null && twbn != null){

            BufferedImage image = (BufferedImage) gambar;

            // merubah ukuran gambar & twibbon untuk tampilan diprogram
            BufferedImage overlay = resize((BufferedImage) twbn,  gambar.getWidth(null),
                    gambar.getHeight(null));
            BufferedImage hasil = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = hasil.createGraphics();

            // foto di-draw pada program
            g.drawImage(image, 0,0,null);

            // mengkomposisi elemen visual
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) opacity/100));

            // sebagai penentu posisi twibbon/logo/watermark
            if(posisi==1){ // tengah
                g.drawImage(overlay, gambar.getWidth(null)/2 - overlay.getWidth()/2,
                        gambar.getHeight(null)/2 - overlay.getHeight()/2,null);
            } else if(posisi==2) //atas kiri
            {
                g.drawImage(overlay,50,50,null);
            }
            else if(posisi==3) //atas kanan
            {
                g.drawImage(overlay,gambar.getWidth(null)-overlay.getWidth()-50,50,null);
            }
            else if(posisi==4) //bawah kiri
            {
                g.drawImage(overlay,50,gambar.getHeight(null)-overlay.getHeight()-50,null);
            }
            else if(posisi==5)//bawah kanan
            {
                g.drawImage(overlay,gambar.getWidth(null)-overlay.getWidth() - 50,
                        gambar.getHeight(null)-overlay.getHeight() - 50,null);
            }

            // hasil dari foto & twibbon masukkan ke gambarJadi
            gambarJadi = hasil;
        }
        // jika hanya gambar saja yang diinput ke program
        else if(gambar != null){
            gambarJadi = gambar;
        }
    }

    /**
     *
     * @param gmbr file gambar atau twibbon
     * @param lebar lebar dari gambar atau twibbon
     * @param tinggi tinggi gambar atau twibbon
     * @return
     */
    private static BufferedImage resize(BufferedImage gmbr, int lebar, int tinggi){
        // jika gambar sudah dimasukkan
        if(gmbr !=null){
            // dimensi gambar di-resize
            Dimension resizedDimensions = resize(gmbr.getWidth(),gmbr.getHeight(), lebar,tinggi);
            lebar = (resizedDimensions.width * ukuran)/100;
            tinggi = (resizedDimensions.height * ukuran)/100;

            //membuat gambar dengan lebar & panjang tertentu dengan kualitas smooth
            Image tmp = gmbr.getScaledInstance(lebar,tinggi,Image.SCALE_SMOOTH);

            BufferedImage resized = new BufferedImage(lebar,tinggi,BufferedImage.TYPE_INT_ARGB);

            //membuat graphics2D untuk men-draw BufferedImage
            Graphics2D graphics2D = resized.createGraphics();

            // men-draw gambar dari hasil tmp
            graphics2D.drawImage(tmp, 0 , 0, null);
            // untuk efisiensi saat penggunaan objek graphics
            graphics2D.dispose();

            return resized;
        }
        //return null
        return gmbr;
    }

    /**
     * method ini untuk merubah ukuran gambar dan twibbon
     * sesuai dengan batas panjang & tinggi yang sudah di tentukan
     * @param lebarAsli lebar gambar/twibbon yang asli
     * @param tinggiAsli tinggi gambar/twibbon yang asli
     * @param batasLebar batas lebar yang sudah ditentukan diprogram
     * @param batasTinggi batas tinggi yang sudah ditentukan diprogram
     * @return gambar atau twibbon yang sudah dirubah ukurannya
     */
    public static Dimension resize(int lebarAsli, int tinggiAsli, int batasLebar, int batasTinggi){
        int lebarBaru = lebarAsli;
        int tinggiBaru = tinggiAsli;

        // formula untuk merubah ukuran gambar/twibbon sesuai dengan batas lebar & tinggi

        if(lebarAsli > batasLebar){
            lebarBaru = batasLebar;
            tinggiBaru = lebarBaru * tinggiAsli / lebarAsli;
        }
        if(tinggiBaru > batasTinggi){
            tinggiBaru = batasTinggi;
            lebarBaru = tinggiBaru * lebarAsli / tinggiAsli;
        }
        if(lebarBaru == lebarAsli && tinggiBaru == tinggiAsli){
            if(lebarBaru * tinggiAsli / lebarAsli > tinggiBaru * lebarAsli/ tinggiAsli){
                tinggiBaru = batasTinggi;
                lebarBaru = tinggiBaru *  lebarAsli / tinggiAsli;
            } else{
                lebarBaru = batasLebar;
                tinggiBaru = lebarBaru * tinggiAsli / lebarAsli;
            }
        }
        return new Dimension(lebarBaru,tinggiBaru);
    }
}
