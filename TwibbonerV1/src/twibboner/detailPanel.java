/**
 *   Class ini memiliki banyak method penting
 *   untuk menjalankan program ini, seperti tombol
 *   label, slider, file chooser
 *
 *  Author  : Thomas Dwi Awaka
 *  Version : 1
 */

package twibboner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.IOException;

public class detailPanel extends JPanel{
    private frameUtama frameUtama;
    private display display = new display();

    //Komponen
    private JLabel labelPilihGambar = new JLabel("Tidak ada file terpilih");
    private JButton tombolPilihGambar = new JButton("Pilih Foto");
    private JLabel labelPilihTwibbon = new JLabel("Tidak ada file terpilih");
    private JButton tombolPilihTwibbon = new JButton("Pilih Twibbon");
    private JLabel labelSliderOpacity = new JLabel("Opacity");
    private JSlider sliderOpacity = new JSlider(0, 100, 50);
    private JLabel labelSliderUkuran = new JLabel("Ukuran");
    private JSlider sliderUkuran = new JSlider(10, 110, 65);
    private JCheckBox checkBoxTengah = new JCheckBox("Tengah", true);
    private JCheckBox checkBoxAtasKiri = new JCheckBox("Atas Kiri", false);
    private JCheckBox checkBoxAtasKanan = new JCheckBox("Atas Kanan", false);
    private JCheckBox checkBoxBawahKiri = new JCheckBox("Bawah Kiri", false);
    private JCheckBox checkBoxBawahKanan = new JCheckBox("Bawah Kanan", false);
    private JButton tombolApply = new JButton("Apply");
    private JButton tombolSimpan = new JButton("Simpan");
    private JLabel labelTombolSimpan = new JLabel();

    static String pesanSistem;

    //constructor
    public detailPanel(frameUtama frameUtama) {
        this.frameUtama = frameUtama;

        twibbon.ukuran = 100;
        twibbon.opacity = 100;

        //action listening
        fileListener(tombolPilihGambar, labelPilihGambar);
        fileListener(tombolPilihTwibbon, labelPilihTwibbon);
        checkBoxListener(checkBoxTengah,checkBoxAtasKanan,
                checkBoxAtasKiri,checkBoxBawahKanan,checkBoxBawahKiri);
        sliderListener(sliderUkuran);
        sliderListener(sliderOpacity);
        applyListener(tombolApply);
        saveListener(tombolSimpan,labelTombolSimpan);

        tambahKomponen();
        pesanSistem = "Masukkan Foto";
        display.draw();

    }

    /**
     * method ini memakai grid
     * sehingga setiap tombol,label, dan slidernya
     * bisa tersusun rapi
     */
    public void tambahKomponen(){

        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());

        // bagian memilih foto
        c.gridy = 0;
        add(tombolPilihGambar, c);
        c.gridy = 1;
        add(labelPilihGambar, c);

        // bagian memilih twibbon/logo/watermark
        c.insets = new Insets(20, 0, 0, 0);
        c.gridy = 2;
        add(tombolPilihTwibbon, c);

        c.insets = new Insets(1, 0, 0, 0);
        c.gridy = 3;
        add(labelPilihTwibbon, c);

        // bagian slider
        c.insets = new Insets(30, 0, 0, 0);
        c.gridy = 4;
        add(labelSliderUkuran, c);
        c.insets = new Insets(5, 0, 0, 0);
        c.gridy = 5;
        add(sliderUkuran, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 6;
        add(labelSliderOpacity, c);
        c.insets = new Insets(5, 0, 0, 0);
        c.gridy = 7;
        add(sliderOpacity, c);

        // bagian checkbox
        c.gridy = 8;
        add(checkBoxTengah, c);
        c.gridy = 9;
        add(checkBoxAtasKiri, c);
        c.gridy = 10;
        add(checkBoxAtasKanan, c);
        c.gridy = 11;
        add(checkBoxBawahKiri, c);
        c.gridy = 12;
        add(checkBoxBawahKanan, c);

        // bagian apply dan simpan
        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 13;
        add(tombolApply, c);
        c.gridy = 15;
        c.insets = new Insets(10, 0, 0, 0);
        add(tombolSimpan, c);
    }

    /**
     *  method untuk
     * @return display ke frame utama
     */
    public display getDisplay() {
        return display;
    }

    /**
     * method ini menjalankan method pack dalam Java.AWT.Window
     * untuk menjaga ukuran dari frame yang sudah ditetapkan, seperti setSize()
     */
    public void pack() {
        this.frameUtama.pack();
    }

    /**
     * method untuk listener saat tombol melakukan action (ditekan)
     * @param btn jenis tombol
     * @param label jenis label pada tombol
     */
    public void fileListener(JButton btn, JLabel label){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    // untuk membuka FileDialog yang berfungsi untuk meng-input gambar
                    FileDialog dialog = new FileDialog((Frame) null, "Pilih File untuk dibuka");
                    dialog.setMode(FileDialog.LOAD);
                    dialog.setVisible(true);

                    // tipe data yang digunakan adalah .png / .jpg
                    if (dialog.getFile().endsWith(".png") || dialog.getFile().endsWith(".jpg")
                            || dialog.getFile().endsWith(".PNG") || dialog.getFile().endsWith(".JPG")) {
                        label.setText(dialog.getFile());
                        label.setVisible(true);

                        //jika tombol yang ditekan adalah tombol untuk meng-input gambar
                        if (btn == tombolPilihGambar) {
                            if(twibbon.twbn!=null) pesanSistem = "Edit Foto";
                            else pesanSistem = "Masukkan Twibbon";
                            display.setGambar(dialog.getDirectory() + dialog.getFile());
                            display.draw();
                            twibbon.update();
                            pack();
                        }
                        //jika tombol yang ditekan adalah tombol untuk meng-input twibbon
                        if (btn == tombolPilihTwibbon) {
                            display.setTwbn(dialog.getDirectory() + dialog.getFile());
                            twibbon.update();
                            pesanSistem = "Edit Foto";
                            display.draw();
                            pack();
                        }
                    }
                    // jika file yang dimasukkan bukan .png / .jpg
                    // akan menampilkan pesan diatas preview
                    else {
                        pesanSistem =("File harus format .png atau .jpg");
                        display.draw();
                    }
                } catch (NullPointerException nullPointerException) {
                }
            }
        });
    }

    /**
     * Tempat centang box, sebagai pengatur posisi twibbon
     * @param checkBoxTengah     twibbon diposisi tengah
     * @param checkBoxAtasKanan  twibbon diposisi atas kanan
     * @param checkBoxAtasKiri   twibbon diposisi atas kiri
     * @param checkBoxBawahKanan twibbon diposisi bawah kanan
     * @param checkBoxBawahKiri  twibbon diposisi bawah kiri
     */
    public void checkBoxListener(JCheckBox checkBoxTengah,JCheckBox checkBoxAtasKanan,
                                 JCheckBox checkBoxAtasKiri,JCheckBox checkBoxBawahKanan,JCheckBox checkBoxBawahKiri){
        checkBoxTengah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxAtasKanan.setSelected(false);
                checkBoxAtasKiri.setSelected(false);
                checkBoxBawahKiri.setSelected(false);
                checkBoxBawahKanan.setSelected(false);

                if(!checkBoxTengah.isSelected() && !checkBoxAtasKanan.isSelected()
                && !checkBoxAtasKiri.isSelected() && !checkBoxBawahKanan.isSelected()
                        && !checkBoxBawahKiri.isSelected())
                    checkBoxTengah.setSelected(true);

                twibbon.posisi = 1;
            }
        });

        checkBoxAtasKiri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxAtasKanan.setSelected(false);
                checkBoxTengah.setSelected(false);
                checkBoxBawahKiri.setSelected(false);
                checkBoxBawahKanan.setSelected(false);

                if(!checkBoxTengah.isSelected() && !checkBoxAtasKanan.isSelected()
                        && !checkBoxAtasKiri.isSelected() && !checkBoxBawahKanan.isSelected()
                        && !checkBoxBawahKiri.isSelected())
                    checkBoxAtasKiri.setSelected(true);

                twibbon.posisi = 2;
            }
        });

        checkBoxAtasKanan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxTengah.setSelected(false);
                checkBoxAtasKiri.setSelected(false);
                checkBoxBawahKiri.setSelected(false);
                checkBoxBawahKanan.setSelected(false);

                if(!checkBoxTengah.isSelected() && !checkBoxAtasKanan.isSelected()
                        && !checkBoxAtasKiri.isSelected() && !checkBoxBawahKanan.isSelected()
                        && !checkBoxBawahKiri.isSelected())
                    checkBoxAtasKanan.setSelected(true);

                twibbon.posisi = 3;
            }
        });

        checkBoxBawahKiri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxAtasKanan.setSelected(false);
                checkBoxAtasKiri.setSelected(false);
                checkBoxTengah.setSelected(false);
                checkBoxBawahKanan.setSelected(false);

                if(!checkBoxTengah.isSelected() && !checkBoxAtasKanan.isSelected()
                        && !checkBoxAtasKiri.isSelected() && !checkBoxBawahKanan.isSelected()
                        && !checkBoxBawahKiri.isSelected())
                    checkBoxBawahKiri.setSelected(true);

                twibbon.posisi = 4;
            }
        });

        checkBoxBawahKanan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxAtasKanan.setSelected(false);
                checkBoxAtasKiri.setSelected(false);
                checkBoxTengah.setSelected(false);
                checkBoxBawahKiri.setSelected(false);

                if(!checkBoxTengah.isSelected() && !checkBoxAtasKanan.isSelected()
                        && !checkBoxAtasKiri.isSelected() && !checkBoxBawahKanan.isSelected()
                        && !checkBoxBawahKiri.isSelected())
                    checkBoxBawahKanan.setSelected(true);

                twibbon.posisi = 5;
            }
        });
    }


    /**
     * Method ini menggunakan slider untuk mengatur opacity dan ukuran
     * dari twibbon
     * @param slider pengatur tingkat opacity dan ukuran twibbon
     */
    public void sliderListener(JSlider slider){
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(slider == sliderUkuran) twibbon.ukuran = ((JSlider) e.getSource()).getValue();

                if(slider == sliderOpacity) twibbon.opacity = ((JSlider) e.getSource()).getValue();
            }
        });
    }

    /**
     * mengaplikasikan setiap perubahan yang dilakukan user
     * pada hasil gambar
     * @param tombol membaca action saat ditekan oleh user
     */
    public void applyListener(JButton tombol){
        tombol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twibbon.update();
                display.draw();
                pack();
            }
        });
    }

    /**
     * Untuk menjalankan tombol Simpan, dan tentu saja ini untuk menyimpan gambar
     * @param tombol membaca action saat menekan tombol save
     * @param label sebagai penanda jika berhasil disimpan
     */
    public void saveListener(JButton tombol, JLabel label) {
        tombol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pesanSistem.equals("")) {

                    BufferedImage bufferedImage = (BufferedImage) twibbon.getGambar();
                    
                    try {
                        // file chooser dipakai untuk menyimpan hasil gambar
                        JFrame parentFrame = new JFrame();
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileFilter(new PNGFileFilter());
                        fileChooser.setDialogTitle("Pilih File untuk Menyimpan");

                        int userSelection = fileChooser.showSaveDialog(parentFrame);

                        //saat sudah memilih lokasi & nama file untuk disimpan
                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File outputfile = fileChooser.getSelectedFile();

                            if(!outputfile.getName().endsWith(".png"))
                            outputfile = new File(outputfile.getAbsolutePath() +".png");

                            //hasil gambar akan di-write dengan format .png, di direktori yang sudah dipilih user
                            ImageIO.write(bufferedImage, "png", outputfile);
                            pesanSistem = outputfile.getName() + " telah tersimpan di "
                                    + outputfile.getAbsolutePath();
                            display.draw();
                        }

                    } catch (IOException e1) {
                        pesanSistem = "Gambar Gagal Tersimpan";
                        display.draw();
                    }
                    pack();
                }
            }
        });
    }

    /**
     * Method yang membuat filter file saat file akan disimpan
     * kali ini menggunakan .png karena sesuai kebutuhan
     */
    private static class PNGFileFilter extends FileFilter
    {
        //mengisi format file saat disimpan nanti
        public boolean accept(File file)
        {

            return file.getName().toLowerCase().endsWith(".png") || file.isDirectory();
        }

        public String getDescription()
        {
            return "PNG image  (*.png) ";
        }
    }
}