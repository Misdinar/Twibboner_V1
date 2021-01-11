/**
 *  Class ini menjadi kerangka/frame utama pada program ini
 *
 *  Author  : Thomas Dwi Awaka
 *  Version : 1
 */

package twibboner;

import javax.swing.*;
import java.awt.*;

public class frameUtama extends JFrame {
    private display display;
    private Container c = getContentPane();
    private detailPanel detailPanel;

    /**
     * Membuat layout dan menambahkan kelas lainnya kedalam
     * frame utama
     *
     * @param judul nama dari program ini
     */
    public frameUtama(String judul){
        super(judul);
        setLayout(new FlowLayout());

        detailPanel = new detailPanel(this);
        display = detailPanel.getDisplay();

        c.add(detailPanel);
        c.add(display);
    }
}
