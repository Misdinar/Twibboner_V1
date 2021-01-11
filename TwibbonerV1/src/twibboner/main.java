/**
 *  TWIBBONER V.1
 *  aplikasi sederhana ini memiliki fungsi utama yaitu mempermudah penggabungan
 *  foto dengan twibbon. Selain itu, Program ini bisa berfungsi untuk
 *  penambahan logo/watermark pada suatu gambar
 *
 *  Author  : Thomas Dwi Awaka
 *  Version : 1
 */

package twibboner;

import javax.swing.*;

public class main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new frameUtama("Twibboner V.1");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setResizable(false);
                frame.pack();
            }
        });
    }
}
